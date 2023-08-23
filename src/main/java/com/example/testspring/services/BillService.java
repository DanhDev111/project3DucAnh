package com.example.testspring.services;


import com.example.testspring.dto.*;
import com.example.testspring.entity.Bill;
import com.example.testspring.entity.BillItem;
import com.example.testspring.entity.Product;
import com.example.testspring.entity.User;
import com.example.testspring.repository.BillRepo;
import com.example.testspring.repository.ProductRepo;
import com.example.testspring.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService  {
    @Autowired
    BillRepo billRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    public void create(BillDTO billDTO){
        User user = userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new);

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setStatus(billDTO.getStatus());
        List<BillItem>  billItems = new ArrayList<>();
        for (BillItemDTO billItemDTO : billDTO.getBillItems()){
            BillItem billItem =  new BillItem();
            billItem.setPrice(billItemDTO.getPrice());
            billItem.setQuantity(billItemDTO.getQuantity());
            billItem.setProduct(productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
            billItems.add(billItem);
        }
        bill.setBillItems(billItems);
        billRepo.save(bill);
    }

    public void update(BillDTO billDTO){
        Bill existingBill = billRepo.findById(billDTO.getId()).orElseThrow(NoResultException::new);
        existingBill.setUser(userRepo.findById(billDTO.getUser().getId()).orElseThrow(NoResultException::new));
        existingBill.setStatus(billDTO.getStatus());

//        existingBill.getBillItems().clear();
//        for (BillItemDTO billItemDTO : billDTO.getBillItems()){
//            BillItem billItem =  new BillItem();
//            billItem.setBill(existingBill);
//            billItem.setPrice(billItemDTO.getPrice());
//            billItem.setQuantity(billItemDTO.getQuantity());
//            billItem.setProduct(productRepo.findById(billItemDTO.getProduct().getId()).orElseThrow(NoResultException::new));
//            existingBill.getBillItems().add(billItem);
//        }


        billRepo.save(existingBill);
    }
    public void delete(int id){
        billRepo.deleteById(id);
    }
    public BillDTO getById(int id){
        Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);
        return convertToDTO(bill);
    }
    public BillDTO convertToDTO(Bill bill){
        return new ModelMapper().map(bill,BillDTO.class);
    }
    public PageDTO<BillDTO> search(SearchBillDateDTO searchDTO){
        Pageable pageable = PageRequest.of(searchDTO.getCurrentPage(),searchDTO.getSize());

        Page<Bill> pageRS = billRepo.searchByDate(searchDTO.getStart(),pageable);

        PageDTO<BillDTO> pageDTO = new PageDTO<>();

        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<BillDTO> billDTOS = pageRS.get().map(b -> convertToDTO(b)).collect(Collectors.toList());

        pageDTO.setData(billDTOS);

        return pageDTO;
    }
    public PageDTO<BillStatisticDTO> thongke(){
        List<Object[]> list = billRepo.thongKeBill();

        PageDTO<BillStatisticDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(1);
        pageDTO.setTotalElements(list.size());

        List<BillStatisticDTO> billStatisticDTOs = new ArrayList<>();

        for (Object[] arr : list) {
            BillStatisticDTO billStatisticDTO = new BillStatisticDTO((long) (arr[0]),
                    String.valueOf(arr[1]) + "/" + String.valueOf(arr[2]));

            billStatisticDTOs.add(billStatisticDTO);
        }

        pageDTO.setData(billStatisticDTOs);

        return pageDTO;
    }
}
