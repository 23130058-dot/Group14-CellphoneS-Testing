/**
 * ASM8 - SP17: Tìm kiếm với đoạn mã script (kiểm tra XSS)
 * (sheet "Search Product" trong Group14_23130058_VanVoChiDat.xlsx) - SV: Văn Võ Chí Đạt - 23130058 - Nhóm 14
 */
import com.cellphones.SearchRunner

SearchRunner.run('SP17', '<script>alert(\'XSS\')</script>', '', 'robust')
