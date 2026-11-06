/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_03_Xem_them_tai_them_san_pham") sang Katalon Groovy.
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
	PL.storeEval('(function(){var els=Array.from(window.document.querySelectorAll(\'button, a, div, span\'));for(var i=0;i<els.length;i++){var e=els[i];var txt=(e.innerText||e.textContent||\'\').trim();if((txt===\'Xem thêm\'||txt.includes(\'Xem thêm\')||e.className.includes(\'btn-show-more\'))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})()', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n1')
	PL.echo('Sau thao tac n1=${n1}')
	PL.storeEval('(Number(storedVars[\'n1\'] || \'${n1}\') > Number(storedVars[\'n0\'] || \'${n0}\')) ? \'PASS\' : \'FAIL: Bam Xem them tai them san pham\'', 'kq10')
	PL.echo('KIEM TRA Bam Xem them tai them san pham: ${kq10}')
} finally {
	PL.endSession()
}
