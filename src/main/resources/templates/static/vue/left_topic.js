new Vue({
    el: '#left1',
    data:{
        topicContent:''
    },
    methods: {
        handleSubmit: function() {
            const topicContent = this.topicContent; // 获取textarea的值
            // 使用Axios将数据发送到后端
            axios.post('/api/sendtopic' , {topicContent:topicContent})
                .then(response => {
                    // 处理成功的响应
                    if (response.data) {
                        this.topicContent = '';
                        alert('消息发送成功！');
                    }else {
                            alert('消息发送失败！');
                        }
                    })
                .catch(error => {
                    // 处理错误
                    console.error('Error sending message:', error);
                });
        }
    }
});