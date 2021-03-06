(function() {
  'use strict';

  angular.module('metaForm')
    .controller('MetaFormController', MetaFormInfoController);

  /* @ngInject */
  function MetaFormInfoController($window, $scope, $q, $timeout, MetaFormService,$filter) {

    var modelCode = $scope.modelCode,  // 模块编码

      dataId = $scope.dataId, // 数据主键
      primaryKey = angular.isArray($scope.primaryKey) ? $scope.primaryKey : ($scope.primaryKey ? [$scope.primaryKey] : []),
      primaryValue = angular.isArray($scope.primaryValue) ? $scope.primaryValue : ($scope.primaryKey ? [$scope.primaryValue] : []),
      primaryData,

      operation = $scope.operation,  // 模块操作，例如：view create edit delete ...
      name = $scope.name;

    // 初始化
    init();



    /////////////////////////////////////////////////////////////////////


    /**
     * 初始化
     */
    function init() {
      // 根据出入参数拼主键对象
      // 如果 dataId 存在，优先使用，生成对象 {id: dataId}
      // 否则使用 primaryKey 和 primaryValue 共同生成
      if (dataId) {
        primaryData = {id: dataId};
      }
      else if (primaryKey && primaryKey.length) {
        // 输入：
        //   primaryKey: [id, name]
        //   primaryValue: [1, 'form']
        // 输出：
        //   {id: 1, name: 'form'}
        primaryData = primaryKey.reduce(function(obj, key, index) {
          obj[key] = primaryValue[index];
          return obj;
        }, {});
      }

      // 每一个表单都必须要定义name，在同一个scope中不能有重复
      // 如果没有定义，会自动根据modelCode和operation生成一个。
      $scope.name = name ? name : 'form_'+modelCode+'_'+operation;

      initForm($scope.name);

      // 获取数据
      refresh();
    }

    /**
     * 等待formly准备好后，和scope进行关联
     * @param name
     */
    function initForm(name) {
      // 将form映射，供外界使用
      // 使用$timeout是因为此刻form指令还未准备好，等待下一个空闲周期获取form
      $timeout(function() {
        $scope.form = $scope[name];

        $scope.handlers = {
          // 重置表单
          reset: $scope.options.resetModel,

          // 刷新表单
          refresh: refresh,

          // 提交表单
          submit: submit,

          back: function() {
            $window.history.back();
          }
        };

       /* console.log($scope.form);*/
      });
    }

    /**
     * 刷新数据
     *
     * @return {Promise}
     */
    function refresh() {
      // 读取数据
      return $scope.formPromise = MetaFormService.load({
        modelCode: modelCode,
        operation: operation,
        primaryData: primaryData
      }).then(function(res) {
        // 映射到数据接口，供其他模块调用
        /*  时间戳转化成年-月-日的形式
        var _date = res.data['signTime'];
        res.data['signTime']=$filter("date")(_date, "yyyy-MM-dd");
        */

        /*   这段代码的作用是更改input输入框的类型
        for(var index in res.fields){
          //res.fields[index].templateOptions.type='date';//datepickerPopup  hidden,email,number,date可以修改input输入框的类型2018-05-23T16:00:00.000Z
          //res.fields[index].templateOptions.datepickerPopup='yyyy-MM-dd'
        }
        */

       /*   这段代码的作用是设置验证规则
        $scope.fields = [
          {
            type: 'input',
            key: 'bar',
            templateOptions: {required: true, label: 'IP Address'},
            expressionProperties: {
              'templateOptions.foo': '$modelValue', // set to the $modelValue of the control
              'templateOptions.required': 'model.foo === "foobar"'
            },
            hideExpression: function($viewValue, $modelValue, scope) {
              return scope.model.baz === 'foobar';
            },
            validators: {
              ipAddress: {
                expression: function($viewValue, $modelValue, scope) {
                  var value = $modelValue || $viewValue;
                  return /(\d{1,3}\.){3}\d{1,3}/.test(value);
                },
                message: '$viewValue + " is not a valid IP Address"'
              },
              notLocalHost: '$viewValue !== "127.0.0.1"'
            },
            validation: {
              messages: {
                required: 'to.label + " is required"'
              }
            }
          }
        ];*/

        $scope.data = res.data;
        $scope.subModelCode = res.subModelCode;
        $scope.fields = res.fields;
        $scope.legend = $scope.legend;
        return $q.resolve(res.data);
      });
    }

    /**
     * 提交表单方法，供其他模块调用
     *
     * @param extraData 扩展数据
     * @return {Promise}
     */
    function submit(extraData) {
      return MetaFormService.submit({
        modelCode: modelCode,
        operation: operation,
        data: angular.extend({}, $scope.data, extraData)
      }).then(function(){
        //返回列表
        $window.history.back();
      });
    }
  }
})();
