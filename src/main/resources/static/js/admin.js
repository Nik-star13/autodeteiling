window.loadNewOrders = async function() {
    try {
        const response = await fetch('/api/orders?status=NEW');
        const orders = await response.json();
        displayOrders(orders);
        console.log(`✅ GET @RequestParam /api/orders?status=NEW: ${orders.length} заказов`);
    } catch(error) {
        console.error('GET @RequestParam ошибка:', error);
    }
}

window.loadOrderById = async function() {
    const id = document.getElementById('orderIdInput').value;
    if (!id) {
        alert('Введите ID заказа');
        return;
    }
    try {
        const response = await fetch(`/api/orders/${id}`);
        const order = await response.json();
        displayOrders([order]);
        console.log(`✅ GET @PathVariable /api/orders/${id}: ${order.customerName}`);
    } catch(error) {
        alert('Заказ не найден');
    }
}

function displayOrders(orders) {
    const tbody = document.getElementById('ordersTable');
    if (!orders || orders.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" class="text-center text-muted p-4">Заявок не найдено</td></tr>';
        return;
    }

    tbody.innerHTML = orders.map(order => `
        <tr>
            <td><strong>${order.id}</strong></td>
            <td>${order.customerName}</td>
            <td>${order.phone}</td>
            <td>${order.carBrand || '—'}</td>
            <td>${order.services ? order.services.join(', ') : '—'}</td>
            <td><strong>${order.totalAmount || 0} BYN</strong></td>
            <td>${order.orderDate ? new Date(order.orderDate).toLocaleString() : '—'}</td>
        </tr>
    `).join('');
}
