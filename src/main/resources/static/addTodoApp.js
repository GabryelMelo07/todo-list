const { createApp, ref, onMounted } = Vue;

const apiUrl = 'http://ec2-44-213-103-70.compute-1.amazonaws.com:8080/todo';

createApp({
    setup() {
        const descricao = ref('');
        const data = ref('');

        const addTodo = () => {
            axios.post(apiUrl, { descricao: descricao.value, data: data.value })
                .then(() => {
                    window.location.href = 'index.html';
                })
                .catch(error => {
                    console.error('Error adding todo:', error);
                });
        };

        return {
            descricao,
            data,
            addTodo
        };
    }
}).mount('#app');
