/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_17_Giu_bo_loc_khi_doi_cach_sap_xep") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.store('.product__price--show, .product-price, .price', 'cssPrice')
	PL.store('samsung', 'brand')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Hãng sản xuất\')', 'r')
	PL.pause(1500)
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Samsung\')', 'r')
	PL.pause(1500)
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Xem kết quả\')', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n1')
	PL.echo('Sau thao tac n1=${n1}')
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Giá Thấp - Cao\')', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n2')
	PL.echo('Sau thao tac n2=${n2}')
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)&&(e.querySelector(\'h3, .product__name\')||e).textContent.toLowerCase().indexOf(\'${brand}\')!==-1}).length', 'nBrand')
	PL.storeEval('(Number(storedVars[\'n2\'] || \'${n2}\') > 0) ? \'PASS\' : \'FAIL: Co ket qua\'', 'kq34')
	PL.echo('KIEM TRA Co ket qua: ${kq34}')
	PL.storeEval('(Number(storedVars[\'nBrand\'] || \'${nBrand}\') === Number(storedVars[\'n2\'] || \'${n2}\')) ? \'PASS\' : \'FAIL: Van chi hien thi hang da loc\'', 'kq35')
	PL.echo('KIEM TRA Van chi hien thi hang da loc: ${kq35}')
	PL.storeEval('(function(){var prices=[];Array.prototype.forEach.call(window.document.querySelectorAll(\'${cssPrice}\'),function(e){if(e.offsetWidth||e.offsetHeight||e.getClientRects().length){var v=parseInt((e.textContent||\'\').replace(/\\D/g,\'\'));if(v)prices.push(v)}});if(prices.length<2) return \'PASS\';for(var i=1;i<prices.length;i++){if(prices[i]<prices[i-1]) return \'FAIL: Gia khong tang dan\'}return \'PASS\'})()', 'kq36')
	PL.echo('KIEM TRA Gia tang dan: ${kq36}')
} finally {
	PL.endSession()
}
