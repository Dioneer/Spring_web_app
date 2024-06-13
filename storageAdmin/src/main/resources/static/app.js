/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/js/modules/SlideToggle.js":
/*!***************************************!*\
  !*** ./src/js/modules/SlideToggle.js ***!
  \***************************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "_slideDown": () => (/* binding */ _slideDown),
/* harmony export */   "_slideToggle": () => (/* binding */ _slideToggle),
/* harmony export */   "_slideUp": () => (/* binding */ _slideUp)
/* harmony export */ });
//SlideToggle
//Организавано на транзишинах анимируемых свойств и изменении display
//Открывает спойлер

let _slideDown = (target, duration = 500) => {
	target.style.removeProperty('display');
	//блок проверки display на "none"
	let display = window.getComputedStyle(target).display;
	if (display === 'none')
		display = 'block';
	target.style.display = display;
	let height = target.offsetHeight;
	//без этого свойства криво отображаются рамки при открытии
	target.style.overflow = 'hidden';
	//временно навешенные свойства для анимации
	target.style.height = 0;
	target.style.paddingTop = 0;
	target.style.paddingBottom = 0;
	target.style.marginTop = 0;
	target.style.marginBottom = 0;
	target.offsetHeight;
	target.style.transitionProperty = "height, margin, padding";
	target.style.transitionDuration = duration + 'ms';
	target.style.height = height + 'px';
	//удалаеят временно навешенные свойства для анимации. С задержкой нельзя, т.к.
	//срузу обнулятся padding and margen
	target.style.removeProperty('padding-top');
	target.style.removeProperty('padding-bottom');
	target.style.removeProperty('margin-top');
	target.style.removeProperty('margin-bottom');
	//Этот блок не обязателен, он удаляет навешенные свойства из консоли и из css
	window.setTimeout(() => {
		target.style.removeProperty('height');
		target.style.removeProperty('overflow');
		target.style.removeProperty('transition-duration');
		target.style.removeProperty('transition-property');
	}, duration);
}

//Закрывает спойлер
let _slideUp = (target, duration = 500) => {
	target.style.transitionProperty = 'height, margin, padding';
	target.style.transitionDuration = duration + 'ms';
	target.style.height = target.offsetHeight + 'px';
	target.offsetHeight;
	//без этого ствойства криво отображаются рамки при закрытии
	target.style.overflow = 'hidden';
	//временно навешенные свойства для анимации
	target.style.height = 0;
	target.style.paddingTop = 0;
	target.style.paddingBottom = 0;
	target.style.marginTop = 0;
	target.style.marginBottom = 0;
	//Этот блок не обязателен, он удаляет навешенные свойства из консоли и из css
	window.setTimeout(() => {
		target.style.display = 'none';
		target.style.removeProperty('height');
		target.style.removeProperty('padding-top');
		target.style.removeProperty('padding-bottom');
		target.style.removeProperty('margin-top');
		target.style.removeProperty('margin-bottom');
		target.style.removeProperty('overflow');
		target.style.removeProperty('transition-duration');
		target.style.removeProperty('transition-property');
	}, duration);
}
let _slideToggle = (target, duration = 500) => {
	if (window.getComputedStyle(target).display === 'none') {
		return _slideDown(target, duration);
	} else {
		return _slideUp(target, duration);
	}
}

/***/ }),

/***/ "./src/js/modules/burger.js":
/*!**********************************!*\
  !*** ./src/js/modules/burger.js ***!
  \**********************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "burger": () => (/* binding */ burger)
/* harmony export */ });
function burger() {
	let burgerButton = document.querySelector('.menu__burger');
	let burgerMenu = document.querySelector('.menu__body');
	if (burgerButton) {
		burgerButton.addEventListener('click', () => {
			burgerButton.classList.toggle('active')
			burgerMenu.classList.toggle('active');
			document.body.classList.toggle('lock');
		})
	}
}

/***/ }),

/***/ "./src/js/modules/data-da.js":
/*!***********************************!*\
  !*** ./src/js/modules/data-da.js ***!
  \***********************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (/* binding */ Adapt)
/* harmony export */ });
// // Dynamic Adapt v.1
// // HTML data-da="where(uniq class name),when(breakpoint),position(digi)"
// // e.x. data-da=".item,992,2"
// // Andrikanych Yevhen 2020
// // https://www.youtube.com/c/freelancerlifestyle



class Adapt {
	constructor(type) {
		let config = { init: true };
		this.type = type;
		this.оbjects = [];
		this.daClassname = "_dynamic_adapt_";
		this.nodes = document.querySelectorAll("[data-da]");
		this.mediaQueries = [];
		this.options = {
			...config,
		}
		this.options.init ? this.init() : null;
	}
	init() {
		for (let i = 0; i < this.nodes.length; i++) {
			let оbject = {};
			// перебор всех переносимых
			const node = this.nodes[i];
			// собираем все то, что лежит в дата атрибуте
			const data = node.dataset.da.trim();
			// собираем массив из дата атрибута
			const dataArray = data.split(",");
			// добавление класов переносимых блоков
			оbject.element = node;
			// добавление родительских класов переносимых блоков
			оbject.parent = node.parentNode;
			// добавление класса куда переносить переноса
			оbject.destination = document.querySelector('.' + dataArray[0].trim());
			// добавление ширины переноса
			оbject.breakpoint = dataArray[1] ? dataArray[1].trim() : "767";
			// добавление порядкогово места в блоке куда переносить
			оbject.place = dataArray[2] ? dataArray[2].trim() : "last";
			// добавление индекса расположения в родительсокм блоке
			оbject.index = this.indexInParent(оbject.parent.children, оbject.element);
			this.оbjects.push(оbject);
		}
		// сортирует от большего к меньшему
		this.arraySort(this.оbjects);

		// массив типа ["(max-width:xxx),ххх"]
		this.mediaQueries = this.оbjects.map(item => {
			return '(' + this.type + "-width: " + item.breakpoint + "px)," + item.breakpoint;
		});
		// массив типа ["(max-width:xxx),ххх"], но уникальных значений
		this.mediaQueries = this.mediaQueries.filter((item, index) => {
			return this.mediaQueries.indexOf(item) === index;
		});
		// навешивание слушателя на медиа-запрос
		// и вызов обработчика при первом запуске
		for (let i = 0; i < this.mediaQueries.length; i++) {
			const media = this.mediaQueries[i];
			const mediaSplit = media.split(',');
			// определние соответсвтия медаивыражению"(min-width: 400px)"
			// иаким образом убрали resize
			const matchMedia = window.matchMedia(mediaSplit[0]);
			const mediaBreakpoint = mediaSplit[1];

			// собираем все объекты, которые подходят под условия
			const оbjectsFilter = this.оbjects.filter((item) => {
				return item.breakpoint === mediaBreakpoint;
			});
			matchMedia.addListener(() => this.mediaHandler(matchMedia, оbjectsFilter));
			this.mediaHandler(matchMedia, оbjectsFilter);
		}
	}

	mediaHandler(matchMedia, оbjects) {
		if (matchMedia.matches) {
			for (let i = 0; i < оbjects.length; i++) {
				const оbject = оbjects[i];
				оbject.index = this.indexInParent(оbject.parent, оbject.element);
				this.moveTo(оbject.place, оbject.element, оbject.destination);
			}
		} else {
			for (let i = 0; i < оbjects.length; i++) {
				const оbject = оbjects[i];
				if (оbject.element.classList.contains(this.daClassname)) {
					this.moveBack(оbject.parent, оbject.element, оbject.index);
				}
			}
		}
	}
	// добавление индекса расположения в родительсокм блоке
	indexInParent(parent, element) {
		return Array.from(parent).indexOf(element);
	}
	// сортирует от большего к меньшему или наоборот, зависит от type
	arraySort(arr) {
		if (this.type === "min") {
			arr.sort((a, b) => {
				if (a.breakpoint === b.breakpoint) {
					if (a.place === b.place) {
						return 0;
					}

					if (a.place === "first" || b.place === "last") {
						return -1;
					}

					if (a.place === "last" || b.place === "first") {
						return 1;
					}

					return a.place - b.place;
				}

				return a.breakpoint - b.breakpoint;
			});
		} else {
			arr.sort((a, b) => {
				if (a.breakpoint === b.breakpoint) {
					if (a.place === b.place) {
						return 0;
					}

					if (a.place === "first" || b.place === "last") {
						return 1;
					}

					if (a.place === "last" || b.place === "first") {
						return -1;
					}

					return b.place - a.place;
				}

				return b.breakpoint - a.breakpoint;
			});
		}
	};
	// вставляем по индексу перед ребенком, если столько детей нет, то в конец
	moveTo(place, element, destination) {
		element.classList.add(this.daClassname);
		if (place === 'last' || place >= destination.children.length) {
			destination.insertAdjacentElement('beforeend', element);
			return;
		}
		if (place === 'first') {
			destination.insertAdjacentElement('afterbegin', element);
			return;
		}
		destination.children[place].insertAdjacentElement('beforebegin', element);
	}

	// вставляем по индексу перед ребенком, если столько детей нет, то в конец
	moveBack(parent, element, index) {
		element.classList.remove(this.daClassname);
		if (parent.children[index] !== undefined) {
			parent.children[index].insertAdjacentElement('beforebegin', element);
		} else {
			parent.insertAdjacentElement('beforeend', element);
		}
	}
}

// let AdaptiveMove = new Adapt("max");


/***/ }),

/***/ "./src/js/modules/functions.js":
/*!*************************************!*\
  !*** ./src/js/modules/functions.js ***!
  \*************************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "isMobile": () => (/* binding */ isMobile),
/* harmony export */   "isWebp": () => (/* binding */ isWebp)
/* harmony export */ });
const isMobile = {
	Android: function () { return navigator.userAgent.match(/Android/i); },
	BlackBerry: function () { return navigator.userAgent.match(/BlackBerry/i); },
	iOS: function () { return navigator.userAgent.match(/iPhone|iPad|iPod/i); },
	Opera: function () { return navigator.userAgent.match(/Opera Mini/i); },
	Windows: function () { return navigator.userAgent.match(/IEMobile/i); },
	any: function () { return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows()); }
};

function isWebp() {
	// Проверка поддержки webp
	function testWebP(callback) {
		let webP = new Image();
		webP.src = "https://www.gstatic.com/webp/gallery/1.webp";

		webP.onload = function () {
			callback(webP.height > 0);
		};
		webP.onerror = function () {
			callback(false);
		};
	}
	// Добавление класса _webp или _no-webp для HTML
	testWebP((support) => {
		let className = support === true ? 'webp' : 'no-webp';
		document.documentElement.classList.add(className);
	});
}

/***/ }),

/***/ "./src/js/modules/ibg.js":
/*!*******************************!*\
  !*** ./src/js/modules/ibg.js ***!
  \*******************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "ibg": () => (/* binding */ ibg)
/* harmony export */ });

//картинка фоном========================================================================================================================
function ibg() {
	let imgbackround = document.querySelectorAll(".ibg");
	if (imgbackround.length > 0) {
		imgbackround.forEach(imgbg => imgbg.style.backgroundImage = 'url(' + imgbg.querySelector('img').getAttribute('src') + ')');
	}
}


/***/ }),

/***/ "./src/js/modules/quantity.js":
/*!************************************!*\
  !*** ./src/js/modules/quantity.js ***!
  \************************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "quantityButtons": () => (/* binding */ quantityButtons)
/* harmony export */ });
let quantityButtons = document.querySelectorAll('.quantity__button');
if (quantityButtons.length > 0) {
	for (let index = 0; index < quantityButtons.length; index++) {
		const quantityButton = quantityButtons[index];
		quantityButton.addEventListener("click", function (e) {
			let value = parseInt(quantityButton.closest('.quantity').querySelector('input').value);
			if (quantityButton.classList.contains('quantity__button_plus')) {
				value++;
			} else {
				value = value - 1;
				if (value < 1) {
					value = 1
				}
			}
			quantityButton.closest('.quantity').querySelector('input').value = value;
		});
	}
}


/***/ }),

/***/ "./src/js/modules/tabs.js":
/*!********************************!*\
  !*** ./src/js/modules/tabs.js ***!
  \********************************/
/***/ ((__unused_webpack___webpack_module__, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "tabs": () => (/* binding */ tabs)
/* harmony export */ });
//Tabs
let tabs = document.querySelectorAll(".tabs");
for (let index = 0; index < tabs.length; index++) {
	let tab = tabs[index];
	let tabs_items = tab.querySelectorAll(".tabs-item");
	let tabs_blocks = tab.querySelectorAll(".tabs-block");
	for (let index = 0; index < tabs_items.length; index++) {
		let tabs_item = tabs_items[index];
		tabs_item.addEventListener("click", function (e) {
			for (let index = 0; index < tabs_items.length; index++) {
				let tabs_item = tabs_items[index];
				tabs_item.classList.remove('active');
				tabs_blocks[index].classList.remove('active');
			}
			tabs_item.classList.add('active');
			tabs_blocks[index].classList.add('active');
			e.preventDefault();
		});
	}
}

/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/************************************************************************/
/******/ 	/* webpack/runtime/define property getters */
/******/ 	(() => {
/******/ 		// define getter functions for harmony exports
/******/ 		__webpack_require__.d = (exports, definition) => {
/******/ 			for(var key in definition) {
/******/ 				if(__webpack_require__.o(definition, key) && !__webpack_require__.o(exports, key)) {
/******/ 					Object.defineProperty(exports, key, { enumerable: true, get: definition[key] });
/******/ 				}
/******/ 			}
/******/ 		};
/******/ 	})();
/******/
/******/ 	/* webpack/runtime/hasOwnProperty shorthand */
/******/ 	(() => {
/******/ 		__webpack_require__.o = (obj, prop) => (Object.prototype.hasOwnProperty.call(obj, prop))
/******/ 	})();
/******/
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/
/************************************************************************/
var __webpack_exports__ = {};
// This entry need to be wrapped in an IIFE because it need to be isolated against other modules in the chunk.
(() => {
/*!***********************!*\
  !*** ./src/js/app.js ***!
  \***********************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _modules_functions_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./modules/functions.js */ "./src/js/modules/functions.js");
/* harmony import */ var _modules_burger_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./modules/burger.js */ "./src/js/modules/burger.js");
/* harmony import */ var _modules_data_da_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./modules/data-da.js */ "./src/js/modules/data-da.js");
/* harmony import */ var _modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./modules/SlideToggle.js */ "./src/js/modules/SlideToggle.js");
/* harmony import */ var _modules_ibg_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./modules/ibg.js */ "./src/js/modules/ibg.js");
/* harmony import */ var _modules_quantity_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./modules/quantity.js */ "./src/js/modules/quantity.js");
/* harmony import */ var _modules_tabs_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./modules/tabs.js */ "./src/js/modules/tabs.js");


;







document.addEventListener('DOMContentLoaded', (e) => {
	//бургер хэдера
	(0,_modules_burger_js__WEBPACK_IMPORTED_MODULE_1__.burger)();
	//перекидывает блоки при адаптиве
	let AdaptiveMove = new _modules_data_da_js__WEBPACK_IMPORTED_MODULE_2__["default"]("max");
	AdaptiveMove.init();
	//картинка фоном
	(0,_modules_ibg_js__WEBPACK_IMPORTED_MODULE_4__.ibg)();
})

//выводит страницы aside меню
if (_modules_functions_js__WEBPACK_IMPORTED_MODULE_0__.isMobile.any()) {
	let menuParents = document.querySelectorAll('.menu-page__parent');
	for (let index = 0; index < menuParents.length; index++) {
		const el = menuParents[index];
		el.addEventListener("click", (e) => {
			el.classList.toggle('active');
			e.preventDefault();
		});
	}
} else {
	let menuParents = document.querySelectorAll('.menu-page__parent');
	for (let i = 0; i < menuParents.length; i++) {
		const el = menuParents[i];
		el.addEventListener("mouseenter", function (e) {
			el.classList.add('active');
		});
		el.addEventListener("mouseleave", function (e) {
			el.classList.remove('active');
		});
	}
}

// aside бургер
let menuBurger = document.querySelector('.menu-page__burger');
let pageBody = document.querySelector('.menu-page__body');
let windowWidth = document.querySelector('.wrapper').offsetWidth;
if (windowWidth <= 992) {
	menuBurger.classList.add('active');
	_modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideUp(pageBody);
}
menuBurger.addEventListener("click", () => {
	menuBurger.classList.toggle('active');
	_modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideToggle(pageBody);
});

// подсветка при выборе chekbox
let checkbox = document.querySelectorAll('.checkbox');
if (checkbox.length > 0) {
	checkbox.forEach(i => {
		i.addEventListener('click', (e) => {
			if (i.lastElementChild.classList.contains('active')) {
				//убираем атрибут checked с input.
				i.firstElementChild.removeAttribute('checked');
			} else {
				//вешаем атрибут checked с input
				i.firstElementChild.setAttribute('checked', '');
			}
			i.lastElementChild.classList.toggle('active');
		});
	});
}

// подсветка при выборе chekbox
let checkbox1 = document.querySelectorAll('.checkbox-section-filter');
if (checkbox1.length > 0) {
	checkbox1.forEach(i => {
		i.addEventListener('click', (e) => {
			if (i.lastElementChild.classList.contains('active')) {
				//убираем атрибут checked с input.
				i.firstElementChild.removeAttribute('checked');
			} else {
				//вешаем атрибут checked с input
				i.firstElementChild.setAttribute('checked', '');
			}
			i.lastElementChild.classList.toggle('active');
			console.log(i);
			i.classList.toggle('active');
		});
	});
}

// скрытие-раскрытие блока категорий
let search = document.querySelector('.search-page__title');
let categories = document.querySelector('.categories-search');
search.addEventListener("click", () => {
	search.parentElement.classList.toggle('active');
	_modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideToggle(categories);
});

// смена надписи категорий
let categoriesSearch = document.querySelectorAll('.categories-search__checkbox');
for (let index = 0; index < categoriesSearch.length; index++) {
	const item = categoriesSearch[index];
	item.addEventListener("click", () => {
		item.classList.toggle('active');
		let count = document.querySelectorAll('.categories-search__checkbox.active');
		if (count.length > 0) {
			search.classList.add('count');
			let searchCount = search.querySelector('.search-page__count');
			searchCount.innerHTML = count.length;
		} else {
			search.classList.remove('count');
		}
	});
}

//проверка на поддержку webp
_modules_functions_js__WEBPACK_IMPORTED_MODULE_0__.isWebp();

//настройка главного слайдера + добавление картинок в точки
if (document.querySelector(".mainslider")) {
	let slider = new Swiper('.mainslider__body', {
		pagination: {
			el: '.mainslider__dotts',
			clickable: true,
		},
		slidesPerView: 1,
		spaceBetween: 0,
		autoHeight: true,
		speed: 800,
		observer: true,
		observeParents: true,
		loop: true,
	});
	let mainsliderImages = document.querySelectorAll(".mainslider__image");
	let bullets = document.querySelectorAll('.mainslider__dotts .swiper-pagination-bullet');
	for (let index = 0; index < mainsliderImages.length; index++) {
		const el = mainsliderImages[index].querySelector('img').getAttribute('src');
		bullets[index].style.backgroundImage = "url('" + el + "')";
	}
}

//настройка второго слайдера
if (document.querySelector(".products-slider__body")) {
	let slider2 = new Swiper('.products-slider__body', {
		navigation: {
			nextEl: '.products-slider__arrow_next',
			prevEl: '.products-slider__arrow_prev',
		},
		pagination: {
			el: '.products-slider__info',
			type: 'fraction',
		},
		slidesPerView: 1,
		spaceBetween: 0,
		autoHeight: true,
		speed: 800,
		observer: true,
		observeParents: true,
		loop: true,
	});
}

//настройка третьего слайдера
if (document.querySelector(".brands-slider__body")) {
	let slider3 = new Swiper('.brands-slider__body', {
		navigation: {
			nextEl: '.brands-slider__arrow_next',
			prevEl: '.brands-slider__arrow_prev',
		},
		breakpoints: {
			320: {
				slidesPerView: 1,
			},
			550: {
				slidesPerView: 2,
			},
			768: {
				slidesPerView: 3,
			},
			992: {
				slidesPerView: 4,
			},
			1180: {
				slidesPerView: 5,
			},
		},
		slidesPerView: 5,
		spaceBetween: 0,
		speed: 800,
		observer: true,
		observeParents: true,
		loop: true,
	});
}

//настройка маленького слайдера в item
let slider5 = new Swiper('.images-product__subslider', {
	slidesPerView: 3,
	spaceBetween: 0,
	speed: 800,
	observer: true,
	observeParents: true,
	loop: true,
});

//настройка основного слайдера в item
if (document.querySelector(".images-product__mainslider")) {
	let slider4 = new Swiper('.images-product__mainslider', {
		slidesPerView: 1,
		spaceBetween: 0,
		thumbs: {
			swiper: slider5,
		},
		speed: 800,
		observer: true,
		observeParents: true,
		loop: true,
	});
}

//Spollers
let blockSpoller = document.querySelector('.spollers')
let blockTitle = document.querySelectorAll('.spoller')
if (blockTitle.length) {
	blockSpoller.addEventListener('click', (e) => {
		let title = e.target.closest('.section-filter__title');
		if (title) {
			let blockBody = title.nextElementSibling;
			e.target.classList.toggle('active');
			if (e.target.classList.contains('active')) {
				return _modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideUp(blockBody);
			} else {
				return _modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideDown(blockBody);
			}
		}
	})
}

//Toggle для фильтрации
if (document.querySelector(".filter__body")) {
	let filter = document.querySelector('.filter__title')
	let filterBody = document.querySelector('.filter__body');
	if (windowWidth <= 992) {
		_modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideUp(filterBody);
	}
	filter.addEventListener("click", () => {
		_modules_SlideToggle_js__WEBPACK_IMPORTED_MODULE_3__._slideToggle(filterBody);
	});
}
})();

/******/ })()
;
//# sourceMappingURL=app.min.js.map