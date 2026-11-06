/**
 * Chức năng "Danh sách sản phẩm" - CellphoneS - Nhóm 14
 * SV: Nguyễn Hữu Huy - 23130136
 * Chuyển từ Selenium IDE (23130136_NguyenHuuHuy_ASM07.html, test case "DSSP_00b_Kham_pha_bo_loc") sang Katalon Groovy.
 * Các hàm PL.* nằm ở Keywords/com/cellphones/productlist/ProductListKeywords.groovy
 */
import com.cellphones.productlist.ProductListKeywords as PL

PL.startSession()
try {
	PL.open('https://cellphones.com.vn/mobile.html')
	PL.pause(6000)
	PL.storeEval('(function(){var L=[\'Hãng sản xuất\', \'Xem theo giá\', \'Xem kết quả\', \'Sẵn hàng\', \'Dung lượng RAM\', \'Bộ nhớ trong\', \'Kích thước màn hình\', \'Giá Thấp - Cao\', \'Giá Cao - Thấp\', \'Phổ biến\', \'Khuyến mãi HOT\'],out=[];L.forEach(function(t){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));var n=0, total=0;els.forEach(function(e){var txt=(e.innerText||e.textContent||\'\').trim();if(txt===t||txt.includes(t)){total++;if(e.offsetWidth||e.offsetHeight||e.getClientRects().length)n++}});out.push(t+\': \'+total+\' tong, \'+n+\' hien thi\')});return out.join(\' ; \')})()', 'lab')
	PL.echo('NHAN BO LOC: ${lab}')
	PL.storeEval('(function(){var els=Array.from(window.document.querySelectorAll(\'button, div, span, a, label, p\'));for(var i=0;i<els.length;i++){var e=els[i];var txt=(e.innerText||e.textContent||\'\').trim();if(txt===\'Hãng sản xuất\'&&(e.offsetWidth||e.offsetHeight||e.getClientRects().length)){var p=e.parentNode||e;return (p.innerText||p.textContent||\'\').replace(/\\s+/g,\' \').slice(0,500)}}return \'khong thay\'})()', 'bar')
	PL.echo('THANH BO LOC: ${bar}')
} finally {
	PL.endSession()
}
