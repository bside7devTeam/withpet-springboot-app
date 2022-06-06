package org.gig.withpet.api.controller.v1;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.gig.withpet.api.utils.ApiResponse;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.AdoptAnimalService;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalListDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.dto.AdoptAnimalSearchDto;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.ProcessStatus;
import org.gig.withpet.core.domain.adoptAnimal.adoptAnimal.types.TerminalStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 2022/06/06
 */
@RestController
@Api(value = "AdoptAnimalRestController V1")
@RequestMapping("/api/v1/adopt-animal")
@RequiredArgsConstructor
public class AdoptAnimalRestController {

    private final AdoptAnimalService adoptAnimalService;


    @GetMapping(value = "", produces ="application/json;charset=UTF-8")
    public ResponseEntity<ApiResponse> getAdoptAnimalPage(
            @RequestParam(value = "noticeSdt", required = true) String noticeSdt,
            @RequestParam(value = "noticeEdt", required = true) String noticeEdt,
            @RequestParam(value = "processStatus", required = true) ProcessStatus processStatus,
            @RequestParam(value = "terminalStatus", required = false) TerminalStatus terminalStatus,
            @RequestParam(value = "page", required = true) int page,
            @RequestParam(value = "size", required = true) int size
    ) {

        AdoptAnimalSearchDto reqParam = AdoptAnimalSearchDto.builder()
                .noticeSdt(noticeSdt)
                .noticeEdt(noticeEdt)
                .processStatus(processStatus)
                .terminalStatus(terminalStatus)
                .page(page)
                .size(size)
                .build();

        Page<AdoptAnimalListDto> pages =  adoptAnimalService.getAdoptAnimalPageDto(reqParam);

        return new ResponseEntity<>(ApiResponse.OK(pages), HttpStatus.OK);
    }
}
