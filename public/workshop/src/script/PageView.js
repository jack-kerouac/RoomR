(function() {

  define(['backbone'], function(Backbone) {
    'use strict';
    return Backbone.View.extend({
      el: '#Mainfd',
      render: function(title, html) {
        $('#Headline').html(title);
        $('title').text("Beispiel-App - " + title);
        this.$el.html(html);
        return this;
      }
    });
  });

}).call(this);
