angular.module('AppResources', ['ngResource'])
        .factory('Crows', function () {

            var jolokia = new Jolokia({url: "/jmx"});

            return {
                'query': function() {
                    return jolokia.search("*:*");
                }
            }
        });
