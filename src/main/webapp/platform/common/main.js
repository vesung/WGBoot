(function(document, $){

    //Disable certain links
    $('a[href^="#"]').click(function (e) {
        e.preventDefault()
    });

    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;

        // Variables privadas
        var links = this.el.find('.link');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
    };

    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this);
        $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
    };

    var accordion = new Accordion($('#accordion'), false);
    $('.submenu li').click(function () {
        $(this).addClass('current').siblings('li').removeClass('current');
    });

    /**
     * 初始化左侧菜单滚动条
     */
    function initSlimscroll() {
        $('#accordion').slimscroll({
            "position":"right",
            "height":"100%",
            "distance":"0",
            "railVisible":false,
            "size":"5px",
            "color":"#999",
            "railOpacity":"0.5",
            "railColor":"#eee"
        });
    }
    

    $(document).on('appReady', function(){
       initSlimscroll();
    });
}(document, window.jQuery));