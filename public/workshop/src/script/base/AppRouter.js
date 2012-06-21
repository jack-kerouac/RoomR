(function() {

  define(['backbone'], function(Backbone) {
    return Backbone.Router.extend({
      initialize: function() {
        var router;
        router = this;
        return $(document).ready(function() {
          return $('body').click(function(event) {
            var el, link;
            el = $(event.target);
            if (el.prop('nodeName').toLowerCase() === 'a') {
              link = el.attr('href');
              if ((link != null) && link !== '' && link[0] === '/') {
                event.preventDefault();
                return router.navigate(link, {
                  trigger: true
                });
              }
            }
          });
        });
      },
      addSection: function(section) {
        return this.route(section.path, section.name, function() {
          return section.render();
        });
      }
    });
  });

}).call(this);
