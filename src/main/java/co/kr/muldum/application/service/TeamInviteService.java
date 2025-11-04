package co.kr.muldum.application.service;

import co.kr.muldum.application.port.in.InviteTeamUseCase;
import co.kr.muldum.application.port.out.LoadUserPort;
import co.kr.muldum.application.port.out.TeamManagementPort;
import co.kr.muldum.application.port.out.TeamSheetFetchPort;
import co.kr.muldum.domain.exception.InvalidParameterException;
import co.kr.muldum.domain.exception.UnregisteredUserException;
import co.kr.muldum.domain.model.Member;
import co.kr.muldum.domain.model.MemberRole;
import co.kr.muldum.domain.model.Role;
import co.kr.muldum.domain.model.Student;
import co.kr.muldum.domain.model.Team;
import co.kr.muldum.domain.model.TeamType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class TeamInviteService implements InviteTeamUseCase {

    private static final Logger log = LoggerFactory.getLogger(TeamInviteService.class);
    private static final Pattern SHEET_ID_PATTERN = Pattern.compile("/spreadsheets/d/([a-zA-Z0-9-_]+)/?");

    private final LoadUserPort loadUserPort;
    private final TeamManagementPort teamManagementPort;
    private final TeamSheetFetchPort teamSheetFetchPort;

    private static final Pattern STUDENT_IDENTIFIER_PATTERN = Pattern.compile("(\\d+)학년\\s*(\\d+)반\\s*(\\d+)번");

    public TeamInviteService(LoadUserPort loadUserPort,
                             TeamManagementPort teamManagementPort,
                             TeamSheetFetchPort teamSheetFetchPort) {
        this.loadUserPort = loadUserPort;
        this.teamManagementPort = teamManagementPort;
        this.teamSheetFetchPort = teamSheetFetchPort;
    }

    @Override
    public void inviteTeam(String googleSheetUrl, String teamType) {
        if (googleSheetUrl == null || googleSheetUrl.trim().isEmpty()) {
            throw new InvalidParameterException("Google Sheet URL은 필수입니다.");
        }

        TeamType type = validateTeamType(teamType);

        String trimmedUrl = googleSheetUrl.trim();
        boolean isMockRequest = trimmedUrl.startsWith("mock://");

        String sheetSource = isMockRequest ? trimmedUrl : toCsvExportUrl(trimmedUrl);
        String csvContent = teamSheetFetchPort.fetchSheet(sheetSource);
        List<TeamInviteRow> rows = parseCsv(csvContent, type);

        if (rows.isEmpty()) {
            throw new InvalidParameterException("Google Sheet에 팀 정보가 존재하지 않습니다.");
        }

        List<TeamInviteAssignment> assignments = resolveAssignments(rows);

        Map<String, List<TeamInviteAssignment>> groupedByTeam = assignments.stream()
                .collect(Collectors.groupingBy(TeamInviteAssignment::teamName));

        for (Map.Entry<String, List<TeamInviteAssignment>> entry : groupedByTeam.entrySet()) {
            String teamName = entry.getKey();
            List<TeamInviteAssignment> members = entry.getValue();

            ensureLeaderExists(teamName, members);

            Team team = teamManagementPort.findByNameAndType(teamName, type)
                    .orElseGet(() -> teamManagementPort.saveTeam(Team.create(teamName, type)));

            List<Member> memberDomains = members.stream()
                    .map(member -> Member.create(team.getTeamId(), member.student().getUserId(), member.role()))
                    .toList();

            teamManagementPort.replaceMembers(team.getTeamId(), memberDomains);
        }

        log.info("Processed {} team invitation row(s) from {} for type {}", rows.size(), googleSheetUrl, type);
    }

    private TeamType validateTeamType(String teamType) {
        if (teamType == null || teamType.trim().isEmpty()) {
            throw new InvalidParameterException("팀 타입", "null");
        }
        try {
            return TeamType.fromCode(teamType.trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("팀 타입", teamType);
        }
    }

    private String toCsvExportUrl(String sheetUrl) {
        Matcher matcher = SHEET_ID_PATTERN.matcher(sheetUrl);
        if (!matcher.find()) {
            throw new InvalidParameterException("유효하지 않은 Google Sheet URL입니다.");
        }

        String sheetId = matcher.group(1);
        String gid = extractGid(sheetUrl).orElse("0");
        return String.format("https://docs.google.com/spreadsheets/d/%s/export?format=csv&gid=%s", sheetId, gid);
    }

    private Optional<String> extractGid(String sheetUrl) {
        try {
            URI uri = new URI(sheetUrl);
            if (uri.getQuery() != null) {
                for (String parameter : uri.getQuery().split("&")) {
                    String[] pair = parameter.split("=", 2);
                    if (pair.length == 2 && "gid".equalsIgnoreCase(pair[0])) {
                        return Optional.of(pair[1]);
                    }
                }
            }

            String fragment = uri.getFragment();
            if (fragment != null) {
                for (String token : fragment.split("&")) {
                    String[] pair = token.split("=", 2);
                    if (pair.length == 2 && "gid".equalsIgnoreCase(pair[0])) {
                        return Optional.of(pair[1]);
                    }
                }
            }
        } catch (URISyntaxException e) {
            throw new InvalidParameterException("유효하지 않은 Google Sheet URL입니다.");
        }

        return Optional.empty();
    }

    private List<TeamInviteRow> parseCsv(String csvContent, TeamType teamType) {
        List<TeamInviteRow> rows = new ArrayList<>();
        String[] lines = csvContent.split("\\r?\\n");
        if (lines.length <= 1) {
            return rows;
        }

        for (int i = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }

            String[] columns = line.split(",", -1);
            if (columns.length < 3) {
                log.warn("Skipping incomplete row: {}", line);
                continue;
            }

            String teamName = columns[0].trim();
            String studentId = columns[1].trim();
            String studentName = columns[2].trim();
            String role = columns.length > 3 ? columns[3].trim() : "";

            if (teamName.isEmpty() || studentId.isEmpty() || studentName.isEmpty()) {
                log.warn("Skipping row with missing mandatory data: {}", line);
                continue;
            }

            String normalizedRole = role.isEmpty() ? MemberRole.MEMBER.name() : role.trim().toUpperCase();
            MemberRole memberRole;
            try {
                memberRole = MemberRole.valueOf(normalizedRole);
            } catch (IllegalArgumentException e) {
                throw new InvalidParameterException("팀 역할", role);
            }

            ParsedStudentIdentifier parsedIdentifier = parseStudentIdentifier(studentId, i + 1);

            rows.add(new TeamInviteRow(
                    i + 1,
                    teamType,
                    teamName,
                    studentId,
                    studentName,
                    memberRole,
                    parsedIdentifier.grade(),
                    parsedIdentifier.classNo(),
                    parsedIdentifier.studentNo()
            ));
        }
        return rows;
    }

    private List<TeamInviteAssignment> resolveAssignments(List<TeamInviteRow> rows) {
        List<TeamInviteAssignment> assignments = new ArrayList<>();

        for (TeamInviteRow row : rows) {
            Student student = loadUserPort.findByGradeAndClassAndStudentNo(row.grade(), row.classNo(), row.studentNo())
                    .filter(user -> user instanceof Student)
                    .map(user -> (Student) user)
                    .orElseThrow(() -> UnregisteredUserException.forStudent(row.grade(), row.classNo(), row.studentNo(), row.rowNumber()));

            if (student.getRole() != Role.STUDENT) {
                throw new InvalidParameterException("학생만 초대할 수 있습니다.");
            }

            if (!student.getName().equals(row.studentName())) {
                log.warn("Name mismatch for studentId {}: sheet='{}', db='{}'", row.studentId(), row.studentName(), student.getName());
            }

            assignments.add(new TeamInviteAssignment(row.teamName(), student, row.role()));
        }

        return assignments;
    }

    private void ensureLeaderExists(String teamName, List<TeamInviteAssignment> assignments) {
        long leaderCount = assignments.stream()
                .filter(assignment -> assignment.role() == MemberRole.LEADER)
                .count();

        if (leaderCount == 0) {
            throw new InvalidParameterException("팀 " + teamName + "에 리더가 존재하지 않습니다.");
        }
    }

    private ParsedStudentIdentifier parseStudentIdentifier(String rawStudentId, int rowNumber) {
        String normalized = rawStudentId.replaceAll("\\s+", "");

        Matcher matcher = STUDENT_IDENTIFIER_PATTERN.matcher(normalized);
        if (matcher.matches()) {
            int grade = Integer.parseInt(matcher.group(1));
            int classNo = Integer.parseInt(matcher.group(2));
            int studentNo = Integer.parseInt(matcher.group(3));
            return new ParsedStudentIdentifier(grade, classNo, studentNo);
        }

        if (normalized.matches("\\d{4}")) {
            int grade = Character.getNumericValue(normalized.charAt(0));
            int classNo = Character.getNumericValue(normalized.charAt(1));
            int studentNo = Integer.parseInt(normalized.substring(2));
            return new ParsedStudentIdentifier(grade, classNo, studentNo);
        }

        throw new InvalidParameterException("학번 형식이 올바르지 않습니다. (행 " + rowNumber + ", 값: " + rawStudentId + ")");
    }

    private record TeamInviteRow(
            int rowNumber,
            TeamType teamType,
            String teamName,
            String studentId,
            String studentName,
            MemberRole role,
            int grade,
            int classNo,
            int studentNo
    ) {
    }

    private record TeamInviteAssignment(String teamName, Student student, MemberRole role) {
    }

    private record ParsedStudentIdentifier(int grade, int classNo, int studentNo) {
    }
}
