(function() {

  define(['backbone', 'User'], function(Backbone, User) {
    'use strict';
    return Backbone.Collection.extend({
      model: User,
      url: 'rest/users'
    });
  });

}).call(this);
