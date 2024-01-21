/**
 * Created by cyb on 2018/4/18.
 */
var _global = (function() {
    function getRootPath() {
        return $('#ROOT_PATH').val();
    }
    function getSiteRootPath() {
        return $('#SITE_ROOT_PATH').val();
    }
    return {
        path: {
            root_path: getRootPath(),
            site_root_path: getSiteRootPath()
        },
        /**
         * 上传单个图片
         * @param server                    服务端链接
         * @param pick                      选择文件的按钮。是选择器（比如：'#filePicker'）
         * @param width                     图片宽度要求（选填）
         * @param height                    图片高度要求（选填）
         * @param thumbWidth                缩略图宽度
         * @param thumbHeight               缩略图高度
         * @param thumbSelector             缩略图展示ID，是选择器（比如：'#fileThumb'）
         * @param successCallback           成功上传之后的处理函数
         * @param errorCallback             失败上传之后的处理函数
         * @returns {{}}
         */
        uploadSingleImg: function (server, pick, width, height, thumbWith, thumbHeight, thumbSelector, successCallback, errorCallback) {
            var uploader = WebUploader.create({
                swf: getRootPath() + '/resources/lib/webuploader/Uploader.swf',   // swf文件路径
                auto: true, // 选完文件后，是否自动上传
                server: getRootPath() + server, // 文件接收服务端
                pick: pick, // 选择文件的按钮。是选择器('.choose_all')
                duplicate: true, // {Boolean} [可选] [默认值：undefined] 去重，根据文件名字、文件大小和最后修改时间来生成hash Key.，所以只需在属性的后面追加duplicate:true  就可以实现重复上传。
                // 限制上传个数
                fileNumLimit: 1,
                // 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });

            // 缩略图
            $(thumbSelector).children('div').children('img').attr('width', thumbWith);
            $(thumbSelector).children('div').children('img').attr('height', thumbHeight);
            $(thumbSelector).children('div').children('img').css({'width': thumbWith, 'height': thumbHeight});

            /**
             * 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
             */
            uploader.on('beforeFileQueued', function(file) {

            });

            /**
             * 当有文件添加进来的时候
             */
            uploader.on('fileQueued', function(file) {
                $(thumbSelector).children('div').attr('id', file.id);
                $(thumbSelector).children('div').children('div').text(file.name);
                // 创建缩略图
                uploader.makeThumb(file, function(error, src) {
                    if (error) {
                        $(thumbSelector).children('div').children('img').replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $(thumbSelector).children('div').children('img').attr('src', src);
                }, thumbWith, thumbHeight);
            });

            /**
             * 文件上传过程中创建进度条实时显示
             */
            uploader.on('uploadProgress', function(file, percentage) {
                var percent = $('#'+file.id).find('.progress span');
                // 避免重复创建
                if (!percent.length) {
                    percent = $('<p class="progress"><span></span></p>').appendTo($('#' + file.id)).find('span');
                }
                percent.css('width', percentage * 100 + '%');
            });

            /**
             * 文件上传成功，给item添加成功class, 用样式标记上传成功。
             */
            uploader.on('uploadSuccess', function(file, data) {
                console.log(file);
                console.log(data);
                try {
                    if(width && height) {
                        if(file._info.width != width || file._info.height != height) {
                            $('#' + file.id).hide();
                            alertMsg.warn("请上传长：" + width + '，宽：' + height + '的图片！');
                            errorCallback(file, data);
                            return false;
                        } else {
                            $('#' + file.id).show();
                            successCallback(file, data);
                        }
                    } else {
                        $('#' + file.id).show();
                        successCallback(file, data);
                    }
                } catch (e) {
                    console.error(e);
                }
            });

            /**
             * 文件上传失败，显示上传出错。
             */
            uploader.on('uploadError', function(file, data) {
                var error = $('#' + file.id).find('div.error');
                // 避免重复创建
                if(!error.length) {
                    error = $('<div class="error"></div>').appendTo($('#' + file.id));
                }
                error.text('上传失败');
                errorCallback(file, data);
            });

            /**
             * 不管成功或者失败，文件上传完成时触发。
             */
            uploader.on('uploadComplete', function(file) {
                $('#' + file.id ).find('.progress').remove();
            });

            /**
             * 当所有文件上传结束时触发。
             */
            uploader.on("uploadFinished", function () {
                uploader.destroy();
                _global.uploadSingleImg(server, pick, width, height, thumbWith, thumbHeight, thumbSelector, successCallback, errorCallback);
            });
        },

        /**
         * 上传多个图片
         * @param server                    服务端链接
         * @param pick                      选择文件的按钮。是选择器（比如：'#filePicker'）
         * @param fileNumLimit              限制上传个数
         * @param thumbListSelector         缩略图列表展示ID，是选择器（比如：'#fileThumb'）
         * @param existImgSelector          已经上传了并存在的个数
         * @param successCallback           成功上传之后的处理函数
         * @param errorCallback             失败上传之后的处理函数
         * @param removeCallback            删除文件时触发的函数
         * @returns {{}}
         */
        uploadImgList: function(server, pick, fileNumLimit, thumbListSelector, existImgSelector, successCallback, errorCallback, removeCallback) {
            var thumbWith = 100;
            var thumbHeight = 100;
            var uploader = WebUploader.create({
                // 选完文件后，是否自动上传。
                auto: true,
                // swf文件路径
                swf: getRootPath() + '/js/Uploader.swf',
                // 文件接收服务端。
                server: getRootPath() + server,
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: pick,
                fileNumLimit: fileNumLimit,
                // 只允许选择图片文件。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });

            /**
             * 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
             */
            uploader.on('beforeFileQueued', function(file) {
                if(fileNumLimit <= $(existImgSelector).length) {
                    return false;
                }
            });

            // 当有文件添加进来的时候
            uploader.on('fileQueued', function(file) {
                var html = '<div id="' + file.id + '" class="file-item thumbnail webuploader-left">' + '<img>' + '<div class="info">' + file.name + '</div>' + '<span class="webuploader-remove-this">删除</span>' + '</div>';
                $(thumbListSelector).append($(html));
                // 创建缩略图
                uploader.makeThumb(file, function(error, src) {
                    if (error) {
                        $(html).find('img').replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $('#' + file.id ).find('img').attr('src', src);
                }, thumbWith, thumbHeight);
            });

            // 文件上传过程中创建进度条实时显示。
            uploader.on('uploadProgress', function(file, percentage) {
                var percent = $('#' + file.id).find('.progress span');
                // 避免重复创建
                if (!percent.length) {
                    percent = $('<p class="progress"><span></span></p>').appendTo($('#' + file.id)).find('span');
                }
                percent.css('width', percentage * 100 + '%');
            });

            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            uploader.on('uploadSuccess', function(file, data) {
                // 点选删除文件
                $('#' + file.id).on('click', '.webuploader-remove-this', function() {
                    uploader.removeFile(file);
                    removeCallback(file, data);
                });
                successCallback(file, data);
            });

            // 文件上传失败，现实上传出错。
            uploader.on('uploadError', function(file, data) {
                var error = $('#' + file.id).find('div.error');
                if (!error.length) {
                    error = $('<div class="error"></div>').appendTo($('#' + file.id));
                }
                error.text('上传失败');
                errorCallback(file, data);
            });

            // 完成上传完了，成功或者失败，先删除进度条。
            uploader.on('uploadComplete', function(file) {
                $('#' + file.id).find('.progress').remove();
            });
        },

        /**
         * 上传单个文件
         * @param server                服务端链接
         * @param pick                  选择文件的按钮。是选择器（比如：'#filePicker'）
         * @param subBtn                上传文件的按钮。是选择器（比如：'#filePicker'）
         * @param fileSelector          文件选择器
         * @param successCallback       成功上传之后的处理函数
         * @param errorCallback         失败上传之后的处理函数
         */
        uploadSingleFile: function(server, pick, subBtn, fileSelector, successCallback, errorCallback) {
            var uploader = WebUploader.create({
                // swf文件路径
                swf: getRootPath() + '/resources/lib/webuploader/Uploader.swf',
                // 文件接收服务端。
                server: getRootPath() + server,
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: pick,
                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,
                duplicate: true, // {Boolean} [可选] [默认值：undefined] 去重，根据文件名字、文件大小和最后修改时间来生成hash Key.，所以只需在属性的后面追加duplicate:true  就可以实现重复上传。
                // 限制上传个数
                fileNumLimit: 1
            });
            var status = 'pending';

            /**
             * 当有文件被添加进队列的时候
             */
            uploader.on('fileQueued', function(file) {
                $(fileSelector).children('div').attr('id', file.id);
                $(fileSelector).children('div').children('h4').text(file.name);
                $(fileSelector).children('div').children('p.state').text('等待上传...');
            });

            /**
             * 文件上传过程中创建进度条实时显示。
             */
            uploader.on('uploadProgress', function(file, percentage) {
                // 避免重复创建
                $('#' + file.id).find('p.state').text('上传中');
                $('#' + file.id).find('.progress .progress-bar').css( 'width', percentage * 100 + '%' );
            });

            /**
             * 成功之后的操作
             */
            uploader.on('uploadSuccess', function(file, data) {
                successCallback(file, data);
            });

            /**
             * 失败的操作
             */
            uploader.on('uploadError', function(file, data) {
                errorCallback(file, data);
            });

            uploader.on('uploadComplete', function(file, data) {
                $('#'+file.id).find('.progress').fadeOut();
            });

            uploader.on('all', function(type) {
                if(type === 'startUpload') {
                    status = 'uploading';
                } else if (type === 'stopUpload') {
                    status = 'paused';
                } else if (type === 'uploadFinished') {
                    status = 'done';
                }
                if(status === 'uploading') {
                    $(subBtn).text('暂停上传');
                } else {
                    $(subBtn).text('开始上传');
                }
            });

            $(subBtn).on('click', function() {
                if (status === 'uploading') {
                    uploader.stop();
                } else {
                    uploader.upload();
                }
            });

            /**
             * 当所有文件上传结束时触发。
             */
            uploader.on("uploadFinished", function () {
                uploader.destroy();
                _global.uploadSingleFile(server, pick, subBtn, fileSelector, successCallback, errorCallback);
            });
        }

    }
})();

