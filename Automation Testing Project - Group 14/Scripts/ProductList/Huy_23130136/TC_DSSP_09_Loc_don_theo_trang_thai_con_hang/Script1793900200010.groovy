/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_09_Loc_don_theo_trang_thai_con_hang") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Sẵn hàng\')', 'r')
	PL.pause(1500)
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'skipped\'})(\'Xem kết quả\')', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n1')
	PL.echo('Sau thao tac n1=${n1}')
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)&&/Sắp về hàng|Hết hàng/.test(e.textContent)}).length', 'nOut')
	PL.echo('The het/sap ve hang=${nOut}')
	PL.storeEval('(Number(storedVars[\'n1\'] || \'${n1}\') > 0) ? \'PASS\' : \'FAIL: Co ket qua\'', 'kq21')
	PL.echo('KIEM TRA Co ket qua: ${kq21}')
	PL.storeEval('(Number(storedVars[\'nOut\'] || \'${nOut}\') === 0) ? \'PASS\' : \'FAIL: Khong con the het hang\'', 'kq22')
	PL.echo('KIEM TRA Khong con the het hang: ${kq22}')
} finally {
	PL.endSession()
}
