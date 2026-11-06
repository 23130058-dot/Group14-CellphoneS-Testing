/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_17_Giu_bo_loc_khi_doi_cach_sap_xep") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.pause(5000)
	PL.storeEval('(function(){ var el = Array.from(document.querySelectorAll(\'button, a, div, span, label\')).find(function(e){ return /^Samsung$/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(el){ var clk = el.closest(\'button, a, label, div\') || el; clk.scrollIntoView({behavior:\'instant\', block:\'center\'}); clk.click(); return \'clicked\'; } return \'skipped\'; })()', 'resBrandFilter')
	PL.pause(3000)
	PL.storeEval('(function(){ var el = Array.from(document.querySelectorAll(\'button, a, div, span, label\')).find(function(e){ return /Giá Thấp - Cao|Thấp đến cao/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(el){ var clk = el.closest(\'button, a, label, div\') || el; clk.scrollIntoView({behavior:\'instant\', block:\'center\'}); clk.click(); return \'clicked\'; } return \'skipped\'; })()', 'resSortInBrand')
	PL.pause(4000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n2')
	PL.echo('Giu bo loc khi doi sap xep: n2=${n2}')
	PL.verifyEval('${n2} > 0', 'true')
} finally {
	PL.endSession()
}
