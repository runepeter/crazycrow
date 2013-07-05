angular.module('CrazyCrowApp', ['AppResources'], function ($routeProvider) {
    $routeProvider.
        when('/about', { controller: AboutCtrl, templateUrl: 'about.html' }).
        when('/contact', { controller: ContactCtrl, templateUrl: 'contact.html' }).
        otherwise({ controller: FrontpageCtrl, templateUrl: 'frontpage.html' });
});
