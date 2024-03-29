package com.application.WorkManagement.controllers.user;

import com.application.WorkManagement.dto.requests.table.TableEntityRequest;
import com.application.WorkManagement.dto.requests.workspace.InviteCodeRequest;
import com.application.WorkManagement.dto.requests.workspace.MemberRequest;
import com.application.WorkManagement.dto.requests.workspace.WorkspaceRequest;
import com.application.WorkManagement.dto.responses.table.TableEntityResponse;
import com.application.WorkManagement.dto.responses.workspace.InviteCodeResponse;
import com.application.WorkManagement.dto.responses.workspace.MemberResponse;
import com.application.WorkManagement.dto.responses.workspace.WorkspaceResponse;
import com.application.WorkManagement.enums.TableSortType;
import com.application.WorkManagement.exceptions.custom.*;
import com.application.WorkManagement.services.Interface.WorkspaceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/workspaces")
@SecurityRequirement(
        name = "JWT-BEARER"
)
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            JwtAuthenticationToken authentication,
            @Valid @RequestBody
            WorkspaceRequest workspaceRequest
    ) throws DataNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    workspaceService.createWorkspace(
                            authentication.getName(),
                            workspaceRequest
                    )
                );
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> readWorkspaceList(
            JwtAuthenticationToken authentication
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.readWorkspaceList(
                                authentication.getName()
                        )
                );
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> readWorkspaceById(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId
    ) throws DataNotFoundException, CustomAccessDeniedException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.readWorkspaceById(
                                authentication.getName(),
                                workspaceId
                        )
                );
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceResponse> updateWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @Valid @RequestBody WorkspaceRequest workspaceRequest
    ) throws DataNotFoundException, CustomAccessDeniedException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.updateWorkspaceById(
                                authentication.getName(),
                                workspaceId,
                                workspaceRequest
                        )
                );
    }

    @PatchMapping("/{workspaceId}/background")
    public ResponseEntity<WorkspaceResponse> updateWorkspaceBackground(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @RequestParam("image") MultipartFile file
    ) throws DataNotFoundException, InvalidFileExtensionException, CustomAccessDeniedException, URISyntaxException, IOException, EmptyImageException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.updateWorkspaceBackground(
                                authentication.getName(),
                                workspaceId,
                                file
                        )
                );
    }

    @DeleteMapping("/{workspaceId}/background")
    public ResponseEntity<WorkspaceResponse> deleteWorkspaceBackground(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId
    ) throws DataNotFoundException, CustomAccessDeniedException, URISyntaxException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.deleteWorkspaceBackground(
                                authentication.getName(),
                                workspaceId
                        )
                );
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<Void> deleteWorkspace(
        JwtAuthenticationToken authentication,
        @PathVariable("workspaceId") UUID workspaceId
    ) throws DataNotFoundException, CustomAccessDeniedException {
        workspaceService.deleteWorkspaceById(
                authentication.getName(),
                workspaceId
        );
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{workspaceId}/members")
    public ResponseEntity<MemberResponse> addMemberFromEmail(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @Valid @RequestBody
            MemberRequest request
    ) throws DataNotFoundException, CustomDuplicateException, CustomAccessDeniedException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    workspaceService.addMemberFromEmail(
                            authentication.getName(),
                            workspaceId,
                            request
                    )
                );
    }

    @PostMapping("/{workspaceId}/invitations")
    public ResponseEntity<InviteCodeResponse> createInviteCodeToJoinInWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @Valid @RequestBody InviteCodeRequest inviteCodeRequest
    ) throws DataNotFoundException, CustomDuplicateException, CustomAccessDeniedException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.createInviteCodeToJoinInWorkspace(
                                authentication.getName(),
                                workspaceId,
                                inviteCodeRequest
                        )
                );
    }

    @GetMapping("/{workspaceId}/invitations")
    public ResponseEntity<InviteCodeResponse> readInviteCode(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId
    ) throws DataNotFoundException, CustomAccessDeniedException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.readInviteCode(
                                authentication.getName(),
                                workspaceId
                        )
                );
    }

    @DeleteMapping("/{workspaceId}/invitations")
    public ResponseEntity<Void> deleteInviteCode(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId
    ) throws DataNotFoundException, CustomAccessDeniedException {
        workspaceService.deleteInviteCode(authentication.getName(), workspaceId);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/{workspaceId}/invitations/{inviteCode}")
    public ResponseEntity<WorkspaceResponse> checkInviteCode(
            @PathVariable("workspaceId") UUID workspaceId,
            @PathVariable("inviteCode") UUID inviteCode
    ) throws DataNotFoundException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.checkInviteCode(
                                workspaceId,
                                inviteCode
                        )
                );
    }

    @PostMapping("/{workspaceId}/invitations/{inviteCode}")
    public ResponseEntity<WorkspaceResponse> joinInWorkspaceFromInviteCode(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @PathVariable("inviteCode") UUID inviteCode
    ) throws DataNotFoundException, CustomDuplicateException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.joinInWorkspaceFromInviteCode(
                                authentication.getName(),
                                workspaceId,
                                inviteCode
                        )
                );
    }

    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<MemberResponse>> readMemberListOfWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @RequestParam(name = "keyword", required = false) String keyword
    ) throws DataNotFoundException, CustomAccessDeniedException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.readMemberListOfWorkspace(
                                authentication.getName(),
                                workspaceId,
                                keyword
                        )
                );
    }

    @PatchMapping("/{workspaceId}/members/{memberId}")
    public ResponseEntity<MemberResponse> updateRoleOfMemberInWorkspace(
        JwtAuthenticationToken authentication,
        @PathVariable("workspaceId") UUID workspaceId,
        @PathVariable("memberId") UUID memberId,
        @Valid @RequestBody InviteCodeRequest request
    ) throws DataNotFoundException, CustomAccessDeniedException, NotExistAdminInWorkspaceException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.updateRoleOfMemberInWorkspace(
                                authentication.getName(),
                                memberId,
                                workspaceId,
                                request
                        )
                );
    }

    @DeleteMapping("/{workspaceId}/members/{memberId}")
    public ResponseEntity<Void> deleteMemberInWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @PathVariable("memberId") UUID memberId
    ) throws DataNotFoundException, NotExistAdminInWorkspaceException, CustomAccessDeniedException {
        workspaceService.deleteMemberInWorkspace(
                authentication.getName(),
                workspaceId,
                memberId
        );
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/{workspaceId}/tables")
    public ResponseEntity<TableEntityResponse> createTableInWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @Valid @RequestBody TableEntityRequest request
    ) throws DataNotFoundException, CustomDuplicateException, CustomAccessDeniedException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.createTableInWorkspace(
                                authentication.getName(),
                                workspaceId,
                                request
                        )
                );
    }

    @GetMapping("/{workspaceId}/tables")
    public ResponseEntity<List<TableEntityResponse>> readTablesInWorkspace(
            JwtAuthenticationToken authentication,
            @PathVariable("workspaceId") UUID workspaceId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", defaultValue = "INCREASE") TableSortType sortType
    ) throws DataNotFoundException, CustomAccessDeniedException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        workspaceService.readTableListInWorkspaceByKeywordAndSortType(
                                authentication.getName(),
                                workspaceId,
                                keyword,
                                sortType
                        )
                );
    }
}
