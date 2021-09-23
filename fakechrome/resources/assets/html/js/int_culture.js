if (document.readyState != 'loading') {
    initEntry('See more', 'See less');
} else if (document.addEventListener) {
    document.addEventListener('DOMContentLoaded', function() {
        initEntry('See more', 'See less');

        img();
    });
} else {
    document.attachEvent('onreadystatechange', function() {
        if (document.readyState != 'loading')
            initEntry('See more', 'See less');
    });
}

function img(){
    //$('img').append('<span class="zoom">123</span>')

}

function initEntry(seeMoreLabel, seeLessLabel) {
    $(".jp-play").click(function(){
        playSound($(this));
    });
    $(".unbox .heading").on("click", function(){
        $(this).parent().toggleClass("is-active");
    });
}

function playSound(btn) {

    btn.css({
        'background-image': 'url(file:///android_asset/html/images/giphy.gif)',
        'background-repeat': 'no-repeat',
        'background-position': 'center center !important',
        'background-size': '80% 100%'
    });

    var src_mp3 = btn.attr("data-src-mp3");
    var src_ogg = btn.attr("data-src-ogg");

    if(btn.hasClass('brefile')){
        if(phat_am_1 != "" && typeof phat_am_1 !== 'undefined'){
            src_mp3 = phat_am_1;
        }
    }else if(btn.hasClass('amefile')){
        if(phat_am_2 != "" && typeof phat_am_2 !== 'undefined'){
            src_mp3 = phat_am_2;
        }
    }

    playHtml5(src_mp3, src_ogg, btn);

}


function playHtml5(src_mp3, src_ogg, btn){
    //use appropriate source
    var audio = new Audio("");
    if (audio.canPlayType("audio/mpeg") != "no" && audio.canPlayType("audio/mpeg") != "")
        audio = new Audio(src_mp3);
    else if (audio.canPlayType("audio/ogg") != "no" && audio.canPlayType("audio/ogg") != "")
        audio = new Audio(src_ogg);

    audio.addEventListener("ended", function(){

        audio.currentTime = 0;

        var at = btn.attr("variant");

        if(at == 'bre'){
            btn.css({
                'background-image': 'url(file:///android_asset/html/images/bre-icon.png)',
                'background-repeat': 'no-repeat',
                'background-position': 'left top !important',
                'background-size': '80% 100%'
            });
        }else if(at == 'ame'){
            btn.css({
                'background-image': 'url(file:///android_asset/html/images/ame-icon.png)',
                'background-repeat': 'no-repeat',
                'background-position': 'left top !important',
                'background-size': '80% 100%'
            });
        }else {
            btn.css({
                'background-image': 'url(file:///android_asset/html/images/exafile-icon.png)',
                'background-repeat': 'no-repeat',
                'background-position': 'left top !important',
                'background-size': '80% 100%'
            });
        }

    });

    audio.addEventListener("playing", function() {
        btn.css({
            'background-image': 'url(file:///android_asset/html/images/ezgif.com-gif-maker.gif)',
            'background-repeat': 'no-repeat',
            'background-position': 'center center !important',
            'background-size': '70% 100%',
        });
    }, true);

    //play
    audio.addEventListener("error", function(e){alert("Apologies, the sound is not available.");});
    audio.play();
}

