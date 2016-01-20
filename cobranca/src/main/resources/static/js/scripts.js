$(function() {
	$('[rel="tooltip"]').tooltip();
	$('.currency').maskMoney({decimal: ',', thousands: '.', allowZero: true});
});

$('.alert-success').fadeTo(3500, 500).slideUp(500, function() {
    $(this).alert('close');
});

$('#confirmarExclusaoModal').on('show.bs.modal', function(event) {
	var botao = $(event.relatedTarget);
	var id = botao.data('id');
	var descricao = botao.data('descricao');
	
	var modal = $(this);
	
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action+id);
	
	var span = modal.find('.modal-body span');
	span.html('Tem certeza que deseja excluir o título <strong>'+descricao+'</strong>?');
});

$('.atualizar-status').on('click', function(event){
	event.preventDefault();
	
	var botao = $(event.currentTarget);
	var urlDestino = botao.attr('href');
	
	var response = $.ajax({
		url: urlDestino,
		type: 'PUT'
	});
	
	response.done(function(e) {
		var id = botao.data('id');
		$('[data-role='+id+']').removeClass('label-danger').addClass('label-success').html(e);
		botao.hide();
	});
});