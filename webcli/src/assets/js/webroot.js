$( document ).ready(function() {

jQuery(document).ready(function() {
	var offset = 250;
	var duration = 300;

	jQuery(window).scroll(function() {
		if (jQuery(this).scrollTop() > offset) {
			jQuery('.back-to-top').fadeIn(duration);
		} else {
			jQuery('.back-to-top').fadeOut(duration);
		}
	});


	jQuery('.back-to-top').click(function(event) {
		event.preventDefault();
		jQuery('html, body').animate({scrollTop: 0}, duration);
		return false;
	});

});




	var appVue = new Vue({
		el: '#app',
		data: {
			currentSlide: 0,
			isPreviousSlide: false,
			isFirstLoad: true,
			slides: [
				{
					headlineFirstLine: "Your",
					headlineSecondLine: "KIDS",
					sublineFirstLine: "For Your",
					sublineSecondLine: "KIDS",
					bgImg: "https://s27.postimg.org/iz6qk21wz/slide0.jpg",
					rectImg: "https://s27.postimg.org/rgouhim83/slide_rect0.jpg"
				},
				{
					headlineFirstLine: "The",
					headlineSecondLine: "GRANDS",
					sublineFirstLine: "For The",
					sublineSecondLine: "GRANDS",
					bgImg: "https://s24.postimg.org/3xfyl0zat/slide1.jpg",
					rectImg: "https://s27.postimg.org/r00xg9gib/slide_rect1.jpg"
				},
				{
					headlineFirstLine: "And...",
					headlineSecondLine: "EVERYONE",
					sublineFirstLine: "",
					sublineSecondLine: "EVERYONE",
					bgImg: "https://s29.postimg.org/80bb1536f/slide2.jpg",
					rectImg: "https://s28.postimg.org/a2i6ateul/slide_rect2.jpg"
				}
				]
		},
		mounted: function () {
			
			console.log($('#app').is(':visible'));
			if($('#app').is(':visible')) { //if the container is visible on the page
				 
			var productRotatorSlide = document.getElementById("app");
			console.log('productRotatorSlide: '+productRotatorSlide);
			var startX = 0;
			var endX = 0;

			productRotatorSlide.addEventListener("touchstart", (event) => startX = event.touches[0].pageX);

			productRotatorSlide.addEventListener("touchmove", (event) => endX = event.touches[0].pageX);

			productRotatorSlide.addEventListener("touchend", function(event) {
				var threshold = startX - endX;

				if (threshold < 150 && 0 < this.currentSlide) {
					this.currentSlide--;
				}
				if (threshold > -150 && this.currentSlide < this.slides.length - 1) {
					this.currentSlide++;
				}
			}.bind(this));
		}  
		},
		methods: {
			updateSlide: function (index) {
				index < this.currentSlide ? this.isPreviousSlide = true : this.isPreviousSlide = false;
				this.currentSlide = index;
				this.isFirstLoad = false;
			}
		}
	});


var mySwiper = new Swiper(".swiper-container", {
	direction: "horizontal",
	loop: true,
	pagination: ".swiper-pagination",
	grabCursor: true,
	speed: 25000,
	paginationClickable: true,
	parallax: true,
	autoplay: true,
	effect: "slide",
	mousewheelControl: 1
});


var swiper = new Swiper('.swiper-container4', { 
	slidesPerView: 3, 
	centeredSlides: true, 
	spaceBetween: 30, 
	pagination: { 
		el: '.swiper-pagination', 
		clickable: true, 
	}, 
	navigation: { 
		nextEl: '.swiper-button-next', 
		prevEl: '.swiper-button-prev', 
	}, 
});

function setIntervalX(callback, delay, repetitions) {
	var x = 0;
	var intervalID = window.setInterval(function () {

		callback();

		if (++x === repetitions) {
			window.clearInterval(intervalID);
		}
	}, delay);
}

var i=1;

var sInt = setInterval(function blinker() {
	if(i==1){
		clearInterval(sInt);
	}
	$('.blink_me').delay(2000);
	$('.blink_me').fadeOut(500);
	$('.blink_me').fadeIn(500);
	i++;

}, 1000);

});



