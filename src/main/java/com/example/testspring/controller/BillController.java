package com.example.testspring.controller;

import com.example.testspring.dto.*;
import com.example.testspring.services.BillService;
import com.example.testspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;


@RestController
public class BillController {
    @Autowired
    BillService billService;
    @PostMapping("/bill/search")// copy lai userlist
    //require = false tuc la khong bat buoc cung duoc
    public ResponseDTO<PageDTO<BillDTO>> search(@RequestBody SearchBillDateDTO searchDTO) {
//        //TODO : KHi reqest quá nhiều param
//        // thì tạo 1 class mới mình request cho nhanh
        PageDTO<BillDTO> pageBill =
                billService.search(searchDTO);
        return ResponseDTO.<PageDTO<BillDTO>>builder().status(200).data(pageBill).build();
    }
    @PostMapping(value = "/admin/bill")
    public ResponseDTO<BillDTO> add(
            @RequestBody BillDTO billDTO
    ) throws IOException {
        billService.create(billDTO);
        return ResponseDTO.<BillDTO>builder().status(200).msg("OK").build();
    }
    @PostMapping("/customer/bill")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<BillDTO> add(
            @RequestBody @Valid BillDTO billDTO, Principal p
            ) throws IOException {
        billService.create(billDTO);
        return ResponseDTO.<BillDTO>builder().status(200).msg("OK").build();
    }

    @DeleteMapping("/admin/bill")
    public ResponseDTO<Void> delete(@RequestParam("id") int id) {
        billService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("OK").build();
    }

    @GetMapping("/admin/bill/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
        //.data chính là đọc từ db lên
        return ResponseDTO.<BillDTO>builder()
                .status(200)
                .data(billService.getById(id))
                .build();
    }

    @PutMapping(value = "/admin/bill")
    public ResponseDTO<BillDTO> update(@RequestBody @Valid BillDTO billDTO) throws IOException {
        billService.update(billDTO);
        return ResponseDTO.<BillDTO>builder()
                .status(200).
                data(billDTO).
                build();
    }

    @GetMapping("/bill/statistic")
    public ResponseDTO<PageDTO<BillStatisticDTO>> billStatistic(){
        PageDTO<BillStatisticDTO> pageDTO = billService.thongke();
        return ResponseDTO.<PageDTO<BillStatisticDTO>>builder()
                .status(200)
                .data(pageDTO)
                .build();
    }
}
