package com.example.testspring.repository;

import com.example.testspring.dto.BillStatisticDTO;
import com.example.testspring.entity.Bill;
import com.example.testspring.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BillRepo extends JpaRepository<Bill,Integer> {
    @Query("SELECT b FROM Bill b WHERE b.createdAt >= :x")
    Page<Bill> searchByDate(@Param("x")Date s, Pageable pageable);
    //Thống kê số lượng đơn hàng trên 1 tháng
    @Query("SELECT count(b.id), month(b.createdAt), year(b.createdAt) "
            + "FROM Bill b GROUP BY month(b.createdAt), year(b.createdAt) ")
    List<Object[]> thongKeBill();

    @Query("SELECT new com.example.testspring.dto.BillStatisticDTO(count(b.id), '/') "
            + " FROM Bill b GROUP BY month(b.createdAt), year(b.createdAt) ")
    List<BillStatisticDTO> thongKeBill2();

    @Query("SELECT p.name,SUM(bi.quantity) AS totalSoldQuanitty FROM Bill b " +
            "JOIN b.billItems bi " +
            "JOIN bi.product p " +
            "WHERE function('MONTH',b.createdAt) =:month " +
            "GROUP BY p.name,p.id" +
            " ORDER BY totalSoldQuanitty Desc"
    )
    List<Object[]> thongKeSPbanchay(@Param("month") int month);
}
