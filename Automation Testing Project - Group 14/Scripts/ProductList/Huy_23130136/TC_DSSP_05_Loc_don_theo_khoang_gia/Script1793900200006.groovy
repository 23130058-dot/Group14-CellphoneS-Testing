/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_05_Loc_don_theo_khoang_gia") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.store('.product-info-container, .product-info, .product-item', 'cssCard')
	PL.store('.product__price--show, .product-price, .price', 'cssPrice')
	PL.store('4000', 'pMinK')
	PL.store('7000', 'pMaxK')
	PL.store('4000000', 'pMin')
	PL.store('7000000', 'pMax')
	PL.pause(6000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n0')
	PL.echo('So the san pham hien thi ban dau n0=${n0}')
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Xem theo giá\')', 'r')
	PL.pause(1500)
	PL.storeEval('(function(){var inputs=Array.from(window.document.querySelectorAll(\'input\')).filter(function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)});if(inputs.length===0) return \'no_input\';var set=Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype,\'value\').set;var vals=[\'${pMinK}\',\'${pMaxK}\'];inputs.forEach(function(el,idx){if(idx<2){el.focus();set.call(el,vals[idx]);el.dispatchEvent(new Event(\'input\',{bubbles:true}));el.dispatchEvent(new Event(\'change\',{bubbles:true}))}});return \'typed \'+inputs.length+\' inputs\'})()', 'r')
	PL.echo('O nhap gia: ${r}')
	PL.pause(800)
	PL.storeEval('(function(txt){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var t=(e.innerText||e.textContent||\'\').trim();if((t===txt||t.includes(txt))&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){e.click();e.dispatchEvent(new MouseEvent(\'click\',{bubbles:true,cancelable:true}));return \'clicked\'}}return \'not_found\'})(\'Xem kết quả\')', 'r')
	PL.pause(4000)
	PL.storeEval('Array.prototype.filter.call(window.document.querySelectorAll(\'${cssCard}\'),function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)}).length', 'n1')
	PL.echo('Sau thao tac n1=${n1}')
	PL.storeEval('(function(){var prices=Array.from(window.document.querySelectorAll(\'${cssPrice}\')).filter(function(e){return !!(e.offsetWidth||e.offsetHeight||e.getClientRects().length)});var pMin=parseInt(storedVars[\'pMin\']||\'${pMin}\')||4000000;var pMax=parseInt(storedVars[\'pMax\']||\'${pMax}\')||7000000;var bad=0;prices.forEach(function(e){var v=parseInt((e.textContent||\'\').replace(/\\D/g,\'\'));if(v && (v < pMin || v > pMax)) bad++});return bad})()', 'badPrice')
	PL.echo('So gia ngoai khoang=${badPrice}')
	PL.storeEval('(Number(storedVars[\'n1\'] || \'${n1}\') > 0) ? \'PASS\' : \'FAIL: Co ket qua\'', 'kq13')
	PL.echo('KIEM TRA Co ket qua: ${kq13}')
	PL.storeEval('(Number(storedVars[\'badPrice\'] || \'${badPrice}\') === 0) ? \'PASS\' : \'FAIL: Tat ca gia nam trong khoang 4-7 trieu\'', 'kq14')
	PL.echo('KIEM TRA Tat ca gia nam trong khoang 4-7 trieu: ${kq14}')
} finally {
	PL.endSession()
}
