/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Trần Xuân Đào - 23130044
 * Chuyển từ Selenium IDE (23130044_NguyenTranXuanDao_ASM07.html, test case "DSSP_05_Loc_don_theo_khoang_gia") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-item, .product-card', 'cssCard')
	PL.store('4000', 'pMinK')
	PL.store('7000', 'pMaxK')
	PL.pause(5000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n0')
	PL.storeEval('(function(minK, maxK){ var btns = Array.prototype.filter.call(document.querySelectorAll(\'button, a, div, span\'), function(e){ return /xem theo giá|khoảng giá|mức giá/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(btns.length > 0){ (btns[0].closest(\'button, a\') || btns[0]).click(); } var inputs = Array.prototype.filter.call(document.querySelectorAll(\'input\'), function(e){ return (e.offsetWidth > 0 || e.offsetHeight > 0) && !/search|tìm/i.test(e.placeholder || \'\'); }); if(inputs.length >= 2){ inputs[0].focus(); inputs[0].value = minK; inputs[0].dispatchEvent(new Event(\'input\', {bubbles:true})); inputs[0].dispatchEvent(new Event(\'change\', {bubbles:true})); inputs[1].focus(); inputs[1].value = maxK; inputs[1].dispatchEvent(new Event(\'input\', {bubbles:true})); inputs[1].dispatchEvent(new Event(\'change\', {bubbles:true})); } var resBtns = Array.prototype.filter.call(document.querySelectorAll(\'button, a, div, span\'), function(e){ return /xem kết quả|áp dụng/i.test(e.textContent.trim()) && (e.offsetWidth > 0 || e.offsetHeight > 0); }); if(resBtns.length > 0){ (resBtns[0].closest(\'button, a\') || resBtns[0]).click(); } return \'done\'; })(\'${pMinK}\', \'${pMaxK}\')', 'resSetPrice')
	PL.pause(4000)
	PL.storeEval('document.querySelectorAll(\'${cssCard}\').length', 'n1')
	PL.echo('Sau loc gia n1=${n1} resSetPrice=${resSetPrice}')
	PL.verifyEval('${n1} > 0', 'true')
} finally {
	PL.endSession()
}
