/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_08_Loc_don_theo_man_hinh") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.pause(5000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n0')
	PL.storeEval('(function(){ var el = Array.from(document.querySelectorAll(\'button, a, div, span, label\')).find(function(e){ return /inch/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(el){ var clk = el.closest(\'button, a, label, div\') || el; clk.scrollIntoView({behavior:\'instant\', block:\'center\'}); clk.click(); return \'clicked\'; } return \'skipped\'; })()', 'resScreen')
	PL.pause(4000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n1')
	PL.echo('Sau loc man hinh: n0=${n0}, n1=${n1} resScreen=${resScreen}')
	PL.verifyEval('${n1} > 0', 'true')
} finally {
	PL.endSession()
}
