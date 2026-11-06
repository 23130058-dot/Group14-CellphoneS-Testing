/**
 * ASM8 - SP18: Tìm kiếm với chuỗi SQL Injection
 * (sheet "Search Product" trong Group14_23130058_VanVoChiDat.xlsx) - SV: Văn Võ Chí Đạt - 23130058 - Nhóm 14
 */
import com.cellphones.SearchRunner

SearchRunner.run('SP18', '\' OR \'1\'=\'1', '', 'robust')
