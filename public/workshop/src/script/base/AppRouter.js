(function() {

  define(['backbone'], function(Backbone) {
    return Backbone.Router.extend({
      initialize: function() {
        var router;
        this.allRoutes = new Array();
        this.currentIndex = 0;
        router = this;
        $(document).ready(function() {
          return $('body').click(function(event) {
            var el, link;
            el = $(event.target);
            if (el.prop('nodeName').toLowerCase() === 'a') {
              link = el.attr('href');
              if ((link != null) && link !== '' && link[0] === '/') {
                event.preventDefault();
                console.log(link);
                this.currentroute = link;
                return router.navigate(link, {
                  trigger: true
                });
              }
            }
          });
        });
        $(window).bind('swiperight', function() {
          return router.navigateBack.call(router);
        });
        return $(window).bind('swipeleft', function() {
          return router.navigateNext.call(router);
        });
      },
      navigateNext: function() {
        var maxRoutes;
        maxRoutes = this.allRoutes.length;
        this.currentIndex = ++this.currentIndex % maxRoutes;
        return this.navigate(this.allRoutes[this.currentIndex], {
          trigger: true
        });
      },
      navigateBack: function() {
        var maxRoutes;
        maxRoutes = this.allRoutes.length;
        this.currentIndex = this.currentIndex > 0 ? this.currentIndex - 1 : maxRoutes - 1;
        return this.navigate(this.allRoutes[this.currentIndex], {
          trigger: true
        });
      },
      addSection: function(section) {
        this.route(section.path, section.name, function() {
          return section.render();
        });
        return this.allRoutes.push(section.path);
      }
    });
  });

}).call(this);
