/* udo.dblclk.js - (c) Pearson Education 2010
 *
 * Portions of code derived from jquery.popupWindow.js (c) Cody Lindley 2009 (Licensed under the MIT license)
 */

(function( $ ){
    $.fn.dblclk = function(options) {
        $.fn.dblclk.defaultSettings = {
            centerBrowser:1, // center window over browser window? {1 (YES) or 0 (NO)}. overrides top and left
            centerScreen:0, // center window over entire screen? {1 (YES) or 0 (NO)}. overrides top and left
            height:400, // sets the height in pixels of the window.
            left:0, // left position when the window appears.
            location:0, // determines whether the address bar is displayed {1 (YES) or 0 (NO)}.
            menubar:0, // determines whether the menu bar is displayed {1 (YES) or 0 (NO)}.
            resizable:1, // whether the window can be resized {1 (YES) or 0 (NO)}. Can also be overloaded using resizable.
            scrollbars:1, // determines whether scrollbars appear on the window {1 (YES) or 0 (NO)}.
            status:0, // whether a status line appears at the bottom of the window {1 (YES) or 0 (NO)}.
            width:350, // sets the width in pixels of the window.
            windowName:'entryPopup', // name of window set from the name attribute of the element that invokes the click
            baseURL:null, // url used for the popup
            top:0, // top position when the window appears.
            toolbar:0 // determines whether a toolbar (includes the forward and back buttons) is displayed {1 (YES) or 0 (NO)}.
        };
        var settings = $.extend({}, $.fn.dblclk.defaultSettings, options || {});

        function cleanText(word) {
            word = word.replace(/^([\d])+/g, "");
            word = word.replace(/([\d])+$/g, "");
            return word;
        }
        function getSelectedText() {
            if(window.getSelection) {
                var userSelection = window.getSelection();
                return userSelection;
            }
            else if(document.getSelection) {
                return document.getSelection();
            }
            else {
                var selection = document.selection && document.selection.createRange();
                if(selection.text) {
                    return selection.text;
                }
                return false;
            }
            return false;
        }

		function escapeSelection(selection) {
			selection = selection.replace(/['???]/, '&apos;');
			return encodeURIComponent(selection);
		}

        return this.each(function() {

            var $this = $(this);

            $this.dblclick(function(event){
                var selected = cleanText(jQuery.trim(getSelectedText()));
                if (selected) {
                    var windowFeatures =
                        'height=' + settings.height +
                        ',width=' + settings.width +
                        ',toolbar=' + settings.toolbar +
                        ',scrollbars=' + settings.scrollbars +
                        ',status=' + settings.status +
                        ',resizable=' + settings.resizable +
                        ',location=' + settings.location +
                        ',menuBar=' + settings.menubar;

                    settings.windowURL = settings.baseURL + escapeSelection(selected);
                    var centeredY,centeredX;
                    if(settings.centerBrowser){
                        if ($.browser.msie) {//hacked together for IE browsers
                            centeredY = (window.screenTop - 120) + ((((document.documentElement.clientHeight + 120)/2) - (settings.height/2)));
                            centeredX = window.screenLeft + ((((document.body.offsetWidth + 20)/2) - (settings.width/2)));
                        }else{
                            centeredY = window.screenY + (((window.outerHeight/2) - (settings.height/2)));
                            centeredX = window.screenX + (((window.outerWidth/2) - (settings.width/2)));
                        }
                        window.open(settings.windowURL, settings.windowName, windowFeatures+',left=' + centeredX +',top=' + centeredY).focus();
                    }
                }
            });
        });
    }


})( jQuery );
