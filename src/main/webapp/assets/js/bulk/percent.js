/**
 * Created by wb on 15-8-28.
 */
(function($){
    $(function(){
        $(".percent-inner-column").each(function(){
            var divText= $(this).text();
            $(this).css("color");
            //debugger
            $(this).css("width",divText);
        })
    });

})(jQuery);