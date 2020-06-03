package springboot.crud.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springboot.crud.data.dto.CancelReasonDto;
import springboot.crud.data.entity.CancelReasonEntity;
import springboot.crud.data.repository.CancelReasonRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Controller
@RequestMapping("cancel-reason")
public class CancelReasonController {

    private CancelReasonRepository cancelReasonRepository;

    @Autowired
    public CancelReasonController(CancelReasonRepository cancelReasonRepository) {
        this.cancelReasonRepository = cancelReasonRepository;
    }

    @GetMapping({"", "/"})
    @ResponseBody
    public ModelAndView showCancelReasonsMain() {

        ModelAndView modelAndView = new ModelAndView("cancel-reason/main");
        modelAndView.addObject("cancelReasons", cancelReasonRepository.findAll());

        return modelAndView;
    }


    @GetMapping("/insert")
    public ModelAndView showInsertPage() {

        ModelAndView modelAndView = new ModelAndView("cancel-reason/insert-page");
        modelAndView.addObject("cancelReasonDto", new CancelReasonDto());
        return modelAndView;
    }

    @PostMapping("/insert")
    public String insertReason(@ModelAttribute @Valid CancelReasonDto dto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "cancel-reason/insert-page";
        }

        CancelReasonEntity newCancelReasonEntity = new CancelReasonEntity();
        newCancelReasonEntity.setDescription(dto.getDescription());

        cancelReasonRepository.save(newCancelReasonEntity);

        return "redirect:/cancel-reason";

    }

    @GetMapping("/edit/{reason_id}")
    public ModelAndView showEditPage(@PathVariable("reason_id") int reasonId) {

        Optional<CancelReasonEntity> maybeCancelReasonEntity = cancelReasonRepository.findById(reasonId);

        if (!maybeCancelReasonEntity.isPresent()) {
            return new ModelAndView("error");
        }

        CancelReasonEntity cancelReasonEntity = maybeCancelReasonEntity.get();

        ModelAndView modelAndView = new ModelAndView("cancel-reason/edit-page");

        modelAndView.addObject("reason_id", reasonId);
        modelAndView.addObject(new CancelReasonDto(cancelReasonEntity.getDescription()));

        return modelAndView;
    }

    @PostMapping("/edit/{reason_id}")
    public String editReason(@PathVariable("reason_id") int reasonId, @ModelAttribute @Valid CancelReasonDto dto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "cancel-reason/edit-page";
        }

        Optional<CancelReasonEntity> maybeCancelReasonEntity = cancelReasonRepository.findById(reasonId);

        if (!maybeCancelReasonEntity.isPresent()) {
            return "error";
        }

        CancelReasonEntity cancelReasonEntityToBeModified = maybeCancelReasonEntity.get();

        cancelReasonEntityToBeModified.setDescription(dto.getDescription());

        cancelReasonRepository.save(cancelReasonEntityToBeModified);

        return "redirect:/cancel-reason";

    }


    @GetMapping("/delete/{reason_id}")
    public String deleteReason(@PathVariable("reason_id") int reasonId) {

        Optional<CancelReasonEntity> maybeCancelReasonEntity = cancelReasonRepository.findById(reasonId);

        if (!maybeCancelReasonEntity.isPresent()) {
            return "error";
        }

        cancelReasonRepository.delete(maybeCancelReasonEntity.get());

        return "redirect:/cancel-reason";
    }

    @GetMapping("/find")
    public ModelAndView showFindByIdPage() {

        ModelAndView modelAndView = new ModelAndView("cancel-reason/find-step-1-page");

        modelAndView.addObject("id", new String());
        return modelAndView;
    }


    @PostMapping("/find")
    public String findReasonById(@ModelAttribute("reason_id") @NotBlank String reasonId,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "cancel-reason/find-step-1-page";
        }

        Optional<CancelReasonEntity> maybeCancelReasonEntity = cancelReasonRepository.findById(Integer.valueOf(reasonId));

        if (!maybeCancelReasonEntity.isPresent()) {
            return "error";
        }

        return "redirect:/cancel-reason/show/" + reasonId;
    }

    @GetMapping("/show/{reason_id}")
    public ModelAndView showFindByIdPage(@PathVariable("reason_id") String reasonId) {

        Optional<CancelReasonEntity> maybeCancelReasonEntity = cancelReasonRepository.findById(Integer.valueOf(reasonId));

        CancelReasonEntity cancelReasonEntity = maybeCancelReasonEntity.get();

        ModelAndView modelAndView = new ModelAndView("cancel-reason/find-step-2-page");
        modelAndView.addObject("reason_id", cancelReasonEntity.getId());
        modelAndView.addObject("description", cancelReasonEntity.getDescription());

        return modelAndView;
    }

}
