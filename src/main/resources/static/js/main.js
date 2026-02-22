document.addEventListener('DOMContentLoaded', function() {
    let selectedServices = [];
    let totalPrice = 0;

    const serviceButtons = document.querySelectorAll('.btn-select-service');
    const servicesList = document.getElementById('servicesList');
    const totalPriceEl = document.getElementById('totalPrice');
    const orderForm = document.getElementById('orderForm');

    serviceButtons.forEach(button => {
        button.addEventListener('click', function() {
            const serviceItem = this.closest('.service-item');
            const serviceName = serviceItem.querySelector('h4').textContent;
            const servicePrice = parseInt(serviceItem.dataset.price);
            const serviceKey = serviceItem.dataset.service;

            const isSelected = selectedServices.some(s => s.key === serviceKey);

            if (isSelected) {
                selectedServices = selectedServices.filter(s => s.key !== serviceKey);
                this.classList.remove('selected');
                this.innerHTML = '<i class="fas fa-plus me-2"></i>Выбрать';
            } else {
                selectedServices.push({ key: serviceKey, name: serviceName, price: servicePrice });
                this.classList.add('selected');
                this.innerHTML = '<i class="fas fa-check me-2"></i>Выбрано';
            }

            updateOrderSummary();
        });
    });

    function updateOrderSummary() {
        if (selectedServices.length === 0) {
            servicesList.innerHTML = '<p class="text-brown mb-0">Услуги не выбраны</p>';
            totalPriceEl.textContent = '0 BYN';
            return;
        }

        let servicesHtml = '';
        totalPrice = 0;

        selectedServices.forEach(service => {
            servicesHtml += `<div class="d-flex justify-content-between mb-2 pb-1 border-bottom border-secondary">
                <span class="text-cream">${service.name}</span>
                <span class="fw-bold premium-gold">${service.price} BYN</span>
            </div>`;
            totalPrice += service.price;
        });

        servicesList.innerHTML = servicesHtml;
        totalPriceEl.textContent = `${totalPrice} BYN`;
    }

    orderForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        if (selectedServices.length === 0) {
            alert('Выберите хотя бы одну услугу');
            return;
        }

        const formData = {
            customerName: document.getElementById('name').value,
            phone: document.getElementById('phone').value,
            carBrand: document.getElementById('carBrand').value,
            services: selectedServices.map(s => s.name)
        };

        try {
            const response = await fetch('/api/orders', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const order = await response.json();
                this.reset();
                selectedServices = [];
                serviceButtons.forEach(btn => {
                    btn.classList.remove('selected');
                    btn.innerHTML = '<i class="fas fa-plus me-2"></i>Выбрать';
                });
                updateOrderSummary();
            } else {
                alert('Ошибка сервера');
            }
        } catch (error) {
            alert('Ошибка сети');
        }
    });

    document.getElementById('phone').addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.startsWith('375')) value = value.substring(3);
        if (value.startsWith('8')) value = value.substring(1);
        if (value.length > 9) value = value.substring(0, 9);
        e.target.value = '+375 (' + value.substring(0,2) + ') ' +
            value.substring(2,5) + '-' +
            value.substring(5,7) + '-' +
            value.substring(7,9);
    });

    document.querySelectorAll('.scroll-link, a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            const target = document.querySelector(targetId);
            if (target) {
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
});