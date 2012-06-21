(function() {
  var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; },
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  define(['base/renderTemplate', 'base/RoomrWidget'], function(renderTemplate, RoomrWidget) {
    'use strict';

    var CreateUserWidget;
    return CreateUserWidget = (function(_super) {

      __extends(CreateUserWidget, _super);

      function CreateUserWidget() {
        this.renderVisibleForm = __bind(this.renderVisibleForm, this);

        this.renderInvisibleForm = __bind(this.renderInvisibleForm, this);

        var _this = this;
        CreateUserWidget.__super__.constructor.call(this, 'createUser');
        this.loginState = 'unknown';
        this.registerEvent('newUserCreated');
        this.subscribeToEvent('loginStateChanged', function(newState) {
          _this.loginState = newState;
          return _this.render();
        });
      }

      CreateUserWidget.prototype.setCreateUserSubmitEvent = function() {
        var _this = this;
        return $('#CreateUserWidgetForm').submit(function(event) {
          var day, mon, userToCreate, year;
          event.preventDefault();
          day = $('#CreateUserWidgetForm input[name=birthdate_day]').val();
          mon = $('#CreateUserWidgetForm input[name=birthdate_mon]').val();
          year = $('#CreateUserWidgetForm input[name=birthdate_year]').val();
          userToCreate = {
            name: $('#CreateUserWidgetForm input[name=name]').val(),
            email: $('#CreateUserWidgetForm input[name=email]').val(),
            password: $('#CreateUserWidgetForm input[name=password]').val(),
            birthdate: "" + year + "-" + mon + "-" + day + "T12:00:00.000+0100",
            gender: $('#CreateUserWidgetForm input[name=gender]').val()
          };
          return $.ajax('/rest/users', {
            contentType: "application/json",
            data: JSON.stringify(userToCreate),
            type: 'POST',
            complete: function(jqXHR, stat) {
              if (stat === 'success') {
                return _this.emit('newUserCreated', userToCreate);
              } else {
                return alert("Irgendwas ging schief. Sorry...");
              }
            }
          });
        });
      };

      CreateUserWidget.prototype.renderInto = function(elem) {
        this.elem = elem;
        return this.render();
      };

      CreateUserWidget.prototype.render = function() {
        if (this.elem != null) {
          if (this.loginState === 'loggedOut') {
            return this.renderVisibleForm();
          } else {
            return this.renderInvisibleForm();
          }
        }
      };

      CreateUserWidget.prototype.renderInvisibleForm = function() {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          return $(_this.elem).empty();
        });
      };

      CreateUserWidget.prototype.renderVisibleForm = function() {
        var _this = this;
        return this.renderTemplate({}, function(html) {
          $(_this.elem).append(html);
          return _this.setCreateUserSubmitEvent();
        });
      };

      return CreateUserWidget;

    })(RoomrWidget);
  });

}).call(this);
