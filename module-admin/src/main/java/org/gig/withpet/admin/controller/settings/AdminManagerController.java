package org.gig.withpet.admin.controller.settings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gig.withpet.core.domain.common.dto.AdministratorCreateForm;
import org.gig.withpet.core.domain.role.RoleService;
import org.gig.withpet.core.domain.role.dto.RoleDto;
import org.gig.withpet.core.domain.user.administrator.AdministratorService;
import org.gig.withpet.core.domain.user.administrator.dto.AdminSearchDto;
import org.gig.withpet.core.domain.user.administrator.dto.AdministratorDetailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2022/05/29
 */
@Controller
@RequestMapping("settings/admin-manager")
@Slf4j
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdministratorService administratorService;
    private final RoleService roleService;

    @GetMapping
    public String list(AdminSearchDto searchDto, Model model) {
        model.addAttribute("pages", administratorService.getAdminPageListBySearch(searchDto));
        model.addAttribute("condition", searchDto);
        return "settings/administrator/list";
    }

    @GetMapping("new")
    public String register(Model model) {
        List<RoleDto> roles = roleService.getAllRoles();
        AdministratorDetailDto dto = AdministratorDetailDto.emptyDto();

        model.addAttribute("roles", roles);
        model.addAttribute("dto", dto);

        return "settings/administrator/editor";
    }

    @GetMapping("check-duplicate/username/{value}")
    @ResponseBody
    public ResponseEntity checkDuplicateData(
            @PathVariable(value = "value") String value) {

        boolean isDuplicate = administratorService.existsUsername(value);
        return ResponseEntity.ok().body(isDuplicate);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@Valid @RequestBody AdministratorCreateForm createForm,
                               Errors errors,
                               RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        Long adminId = administratorService.create(createForm);
        return ResponseEntity.ok().body(adminId);
    }
}
