(function() {

  define(['backbone', 'base/renderTemplate'], function(Backbone, renderTemplate) {
    'use strict';
    return Backbone.View.extend({
      initialize: function(options) {
        return this.template = options.template;
      },
      render: function(target) {
        var context,
          _this = this;
        if (!(target instanceof jQuery)) {
          target = $(target);
        }
        context = {
          id: this.model.get('id'),
          email: this.model.get('email'),
          name: this.model.get('name')
        };
        renderTemplate("user/" + this.template, context, function(html) {
          return _this.$el.html(html).appendTo(target);
        });
        return this;
      }
    });
  });

}).call(this);
