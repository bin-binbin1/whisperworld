new Vue({
    el: '#left1',
    data:{
        topicContent:''
    },
    methods: {
        handleSubmit: function() {
            const topicContent = this.topicContent; // 获取textarea的值
            console.log(' successful');
            // 使用Axios将数据发送到后端
            axios.post('/api/sendtopic', { topicContent })
                .then(response => {
                    // 处理成功的响应
                    console.log('Message sent successfully');
                })
                .catch(error => {
                    // 处理错误
                    console.error('Error sending message:', error);
                });
        }
    }
});