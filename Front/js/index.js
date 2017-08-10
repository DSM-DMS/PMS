var $window = $(window);
var $sidebar = $("#index-sidebar");

// 스크롤 콜백
$window.scroll(function() {
    // 사이드바 스크롤 애니메이션
    $sidebar.stop().animate({
            marginTop: $window.scrollTop() + 30
    });
});
