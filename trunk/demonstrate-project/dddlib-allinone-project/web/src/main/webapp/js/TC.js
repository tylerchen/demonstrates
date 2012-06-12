;
if(typeof(TCManager) === "undefined") {TCManager = {};}
(function($) {
	$.each( {
		UDF : 'undefined',
		FN : 'function',
		STR : 'string',
		NUM : 'number',
		BL : 'boolean',
		OBJ : 'object',
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		userAgent : navigator.userAgent.toLowerCase(),
		isStrict : document.compatMode == 'CSS1Compat',
		isOpera : /opera/,
		isChrome : /chrome/,
		isWebKit : /webkit/,
		isWindows : /windows|win32/,
		isMac : /macintosh|mac os x/,
		isAir : /adobeair/,
		isLinux : /linux/,
		isSecure : /^https/i.test(window.location.protocol),
		__idSeed : 1,
		getIdSeed : function() {
			return 'tcid_'.concat(++$.__idSeed);
		},
		getIndex : function() {
			return (++$.__idSeed);
		},
		isUDF : function(obj) {
			return typeof (obj) == $.UDF;
		},
		isNotNull : function(obj) {
			return typeof (obj) != $.UDF && obj != null;
		},
		isArray : function(obj) {
			return typeof (obj) != $.UDF && (obj instanceof Array);
		},
		isString : function(obj) {
			return typeof (obj) == $.STR;
		},
		isNumber : function(obj) {
			return typeof (obj) == $.NUM;
		},
		isBoolean : function(obj) {
			return typeof (obj) == $.BOOL;
		},
		isObject : function(obj) {
			return typeof (obj) == $.OBJ;
		},
		isDictionary : function(obj) {
			return String(obj).indexOf(" Object") != -1;
		},
		id : function(el) {
			var $el = el ? $(el) : null;
			var elId = $el && $el.size() > 0 ? $el.get(0).id : $.getIdSeed();
			if (!elId) {
				elId = $.getIdSeed();
				$el.get(0).id = elId;
			}
			return elId;
		},
		removeObj : function(obj) {
			for ( var k in obj) {
				try {
					delete obj[k];
				} catch (err) {
				}
			}
			var id = $.getIdSeed();
			window[id] = obj;
			window.setTimeout('try{delete window.' + id + ';}catch(err){}',
					1000);
		},
		getHtml : function($el, removeClass, addClass) {
			var div = $('<div style="display:none"></div>');
			var clone = $($el).clone();
			if (removeClass) {
				clone.removeClass(removeClass);
			}
			if (addClass) {
				clone.addClass(addClass);
			}
			div.append(clone);
			var elHtml = div.html();
			div.remove();
			return elHtml;
		},
		replaceHtmlTag : function(str) {
			return ((str || '') + '').replace(/</gi, '&lt;').replace(/>/gi,
					'&gt;');
		},
		replaceBlock : function(str,arr,blank){
			var i=0, len = arr.length;
			str = str.replace(/\{\w*\}/g,function(block){
				return i<len?arr[i++]:blank==undefined?block:blank;
			});
			return str;
		},
		_ : {},
		__ : {}
	}, function(name, value) {
		if (name in $) {
		} else if (value instanceof RegExp) {
			$[name] = value.test($.userAgent);
		} else {
			$[name] = value;
		}
	});
	$.isSafari = !$.isChrome && /safari/.test($.userAgent);
	$.isSafari3 = $.isSafari && /version\/3/.test($.userAgent);
	$.isSafari4 = $.isSafari && /version\/4/.test($.userAgent);
	$.isIE = !$.isOpera && /msie/.test($.userAgent);
	$.isIE7 = $.isIE && /msie 7/.test($.userAgent);
	$.isIE8 = $.isIE && /msie 8/.test($.userAgent);
	$.isIE9 = $.isIE && /msie 9/.test($.userAgent);
	$.isIE6 = $.isIE && !$.isIE7 && !$.isIE8 && !$.isIE9;
	$.isGecko = !$.isWebKit && /gecko/.test($.userAgent);
	$.isGecko3 = $.isGecko && /rv:1\.9/.test($.userAgent);
	$.isBorderBox = $.isIE && !$.isStrict;
	$.Class = function(members) {
		var fn = function() {
			if (arguments[0] != 'no_init') {
				return this.init.apply(this, arguments);
			}
		};
		fn.prototype = members;
		$.extend(fn, $.Class.prototype);
		return fn;
	};
	$.Class.prototype = {
		extend : function(members) {
			var parent = new this('no_init');
			for (k in members) {
				var prev = parent[k];
				var cur = members[k];
				if (prev && prev != cur && typeof cur == 'function') {
					cur = this._parentize(cur, prev);
				}
				parent[k] = cur;
			}
			return new $.Class(parent);
		},
		implement : function(members) {
			$.extend(this.prototype, members);
		},
		_parentize : function(cur, prev) {
			return function() {
				this.parent = prev;
				return cur.apply(this, arguments);
			};
		}
	};
	$.Object = new $.Class( {
		init : function() {
		}
	});
	$.Widget = $.Object.extend( {
		destroy : function() {
			$.removeObj(this);
		}
	});

	/* IE CollectGarbage */if ($.isIE) {
		setInterval('CollectGarbage', 10000);
	}
	$.extend( {
		/**
		 * 查询浏览器窗口在电脑桌面的坐标{left,top}
		 */
		windowPosition : function() {
			var x = 0, y = 0;
			if (window.screenLeft != undefined) {/* IE,safari,opera */
				x = window.screenLeft;
				y = window.screenTop;
			} else if (window.screenX != undefined) {/* firefox,safari */
				x = window.screenX;
				y = window.screenY;
			}
			return {
				left : x,
				top : y
			};
		},
		/**
		 * 查询可视化窗口大小{width,height}
		 */
		clientArea : function() {
			var width = 0, height = 0;
			if (window.innerWidth != undefined) {
				/* all browser but IE */
				width = window.innerWidth;
				height = window.innerHeight;
			} else if (document.documentElement != undefined
					&& document.documentElement.clientWidth != undefined) {
				/* IE with DOCTYPE */
				width = document.documentElement.clientWidth;
				height = document.documentElement.clientHeight;
			} else if (document.body.clientWidth != undefined) {
				/* IE without DOCTYPE */
				width = document.body.offsetWidth;
				height = document.body.offsetHeight;
			}
			return {
				width : width,
				height : height
			};
		},
		/**
		 * 文档的水平垂直滚动距离{pageX,pageY}
		 */
		scrollRange : function() {
			var h = 0, v = 0;
			if (window.innerWidth != undefined) {
				/* all browser but IE */
				h = window.pageXOffset;
				v = window.pageYOffset;
			} else if (document.documentElement != undefined
					&& document.documentElement.clientWidth != undefined) {
				/* IE with DOCTYPE */
				h = document.documentElement.scrollLeft;
				v = document.documentElement.scrollTop;
			} else if (document.body.clientWidth != undefined) {
				/* IE without DOCTYPE */
				h = document.body.scrollLeft;
				v = document.body.scrollTop;
			}
			return {
				pageX : h,
				pageY : v
			};
		},
		/**
		 * 查询文档的实际高度和宽度{width,height}
		 */
		docArea : function() {
			var width = 0, height = 0;
			if (document.documentElement != undefined
					&& document.documentElement.scrollWidth != undefined) {
				width = document.documentElement.scrollWidth;
				height = document.documentElement.scrollHeight;
			} else if (document.body.scrollWidth != undefined) {
				width = document.body.scrollWidth;
				height = document.body.scrollHeight;
			}
			return {
				width : width,
				height : height
			};
		}
	});
	$.extend( {
		/**
		 * 最大的区域
		 */
		maxArea : function() {
			var c = $.clientArea();
			var s = $.scrollRange();
			var d = $.docArea();
			return {
				width : Math.max(Math.max(c.width, s.pageX), d.width),
				height : Math.max(Math.max(c.height, s.pageY), d.height)
			}
		}
	});
	$.extend( {
		/**
		 * Enter键被按下触发的指定元素按下事件
		 */
		enter : function(event, el2Click) {
			var key = event.keyCode;
			if (key == 13 && el2Click) {
				el2Click.click();
			}
		}
	});
	$.fn.extend( {
		page : function(url, params, callback) {
			if (typeof (url) !== 'string') {
				return this;
			}
			var off = url.indexOf(' ');
			var selector;
			var type = 'GET';
			var self = this;
			if (off > -1) {
				selector = url.slice(off, url.length);
				url = url.slice(0, off);
			}
			if (params) {
				if ($.isFunction(params)) {
					callback = params;
					params = null;
				} else if (typeof (params) === 'object') {
					params = $.param(params, $.ajaxSettings.traditional);
					type = 'POST';
				}
			}
			$.ajax( {
				url : url,
				type : type,
				dataType : 'html',
				data : params,
				contentType : $.contentType,
				complete : function(res, status) {
					if (status === 'success' || status === 'notmodified') {
						if (selector) {
							var reg = /<script(.|\s)*?\/script>/gi;
							var div = $('<div />').append(
									res.responseText.replace(reg, '')).find(
									selector);
							self.html(div);
						} else {
							self.html(res.responseText);
						}
						var children = self.children('.TCExecutable');
						var jsscript = '';
						children.each(function() {
							jsscript = jsscript.concat('{', $(this).attr(
									'script'), '}');
						});
						if (jsscript) {
							eval(jsscript);
						}
					}
					if (callback) {
						self.each(callback, [ res.responseText, status, res ]);
					}
				}
			});
			return this;
		}
	});
	$.extend( {
		json : function() {
			var url, successFunc, data, sync, type, errorFunc;
			for ( var i = 0; i < arguments.length; i++) {
				type = typeof (arguments[i]);
				if (type == 'string') {
					url = arguments[i];
				} else if (type == 'function') {
					if (!successFunc) {
						successFunc = arguments[i];
					} else {
						errorFunc = arguments[i];
					}
				} else if (type == 'boolean') {
					sync = arguments[i];
				} else if (type == 'object') {
					data = arguments[i];
				}
			}
			url = url || '';
			successFunc = successFunc || $.noop;
			type = data ? 'POST' : 'GET';
			data = data || {};
			sync = sync || false;
			$.ajax( {
				data : data,
				url : url,
				type : type,
				timeout : 15000,
				contentType : $.contentType,
				async : !sync,
				cache : false,
				global : false,
				dataType : "json",
				success : function(json) {
					var result = json;
					if (result && result.error) {
						if ($.isFunction(errorFunc)) {
							errorFunc(result);
						} else {
							successFunc(result);
						}
					} else {
						successFunc(result);
					}
				},
				error : function() {
					var result = null;
					try {
						result = eval(arguments[0].responseText);
					} catch (err) {
						result = null;
						alert(arguments[0].responseText);
					}
					if (!result) {
						if ($.isFunction(errorFunc)) {
							errorFunc(arguments[0].responseText);
						}
					} else if (result && result.error) {
						if ($.isFunction(errorFunc)) {
							errorFunc(result);
						}
					} else {
						successFunc(result);
					}
				}
			});
		}
	});
	$.fn.extend( {
		selectOne : function(selectedEl, selectedClass, unSelectedClass) {
			var all = this.each(function() {
				$(this).removeClass(selectedClass).addClass(unSelectedClass);
			});
			$(selectedEl).removeClass(unSelectedClass).addClass(selectedClass);
			return all;
		},
		selectNode : function() {
			return this.each(function() {
				var text = this;
				if ($.browser.msie) {
					var range = document.body.createTextRange();
					range.moveToElementText(text);
					range.select();
				} else if ($.browser.mozilla || $.browser.opera) {
					var selection = window.getSelection();
					var range = document.createRange();
					range.selectNodeContents(text);
					selection.removeAllRanges();
					selection.addRange(range);
				} else if ($.browser.safari) {
					var selection = window.getSelection();
					selection.setBaseAndExtent(text, 0, text, 1);
				}
			});
		},
		disableSelection : function() {
			return this.each(function() {
				var target = this;
				if ('onselectstart' in target) { // IE route
						target.onselectstart = function() {
							return false;
						};
					} else if ('MozUserSelect' in target.style) { // Firefox
						// route
						target.style.MozUserSelect = 'none';
					} else {// All other route (ie: Opera)
						target.onmousedown = function() {
							return false;
						};
					}
					target.style.cursor = 'default';
				});
		}
	});
	$.fn.bgiframe = ($.browser.msie && /msie 6\.0/i.test(navigator.userAgent) ? function(s) {
	    s = $.extend({
	        top     : 'auto', // auto == .currentStyle.borderTopWidth
	        left    : 'auto', // auto == .currentStyle.borderLeftWidth
	        width   : 'auto', // auto == offsetWidth
	        height  : 'auto', // auto == offsetHeight
	        opacity : true,
	        src     : 'javascript:false;'
	    }, s);
	    var html = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="'+s.src+'"'+
	                   'style="display:block;position:absolute;z-index:-1;'+
	                       (s.opacity !== false?'filter:Alpha(Opacity=\'0\');':'')+
	                       'top:'+(s.top=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+\'px\')':prop(s.top))+';'+
	                       'left:'+(s.left=='auto'?'expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+\'px\')':prop(s.left))+';'+
	                       'width:'+(s.width=='auto'?'expression(this.parentNode.offsetWidth+\'px\')':prop(s.width))+';'+
	                       'height:'+(s.height=='auto'?'expression(this.parentNode.offsetHeight+\'px\')':prop(s.height))+';'+
	                '"/>';
	    return this.each(function() {
	        if ( $(this).children('iframe.bgiframe').length === 0 )
	            this.insertBefore( document.createElement(html), this.firstChild );
	    });
	} : function() { return this; });

	// old alias
	$.fn.bgIframe = $.fn.bgiframe;

	function prop(n) {
	    return n && n.constructor === Number ? n + 'px' : n;
	}
})(jQuery);