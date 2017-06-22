package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
	
	@Test
	public void teste() {
		BigDecimal preco = new BigDecimal(0);
		
		preco.add(BigDecimal.valueOf(1));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {
		
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();
		
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		
		Sessao sessao = new Sessao(horario, filme, sala);
		
		Assert.assertFalse(new GerenciadorDeSessao(sessoes).cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();
		
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		
		Sessao sessao = new Sessao(horario.plusHours(1), filme, sala);
		
		Assert.assertFalse(new GerenciadorDeSessao(sessoes).cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesDentroDentroDoHorarioDeUmaSessaoJaExistente() {
		
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		filme.setDuracao(120);
		LocalTime horario = LocalTime.now();
		
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		
		Assert.assertFalse(new GerenciadorDeSessao(sessoes).cabe(new Sessao(horario.plus(1,ChronoUnit.HOURS), filme, sala)));
	}
	
	@Test
	public void garanteQueNaoDevePermitirUmaInsercaoEntreDoisFilmes() {
		
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		
		Filme filme1 = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		filme1.setDuracao(90);
		Sessao SessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), filme1,sala);
		
		Filme filme2 = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		filme2.setDuracao(120);
		Sessao SessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"), filme2,sala);
		
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez, SessaoDasDezoito);
		
		Assert.assertTrue(new GerenciadorDeSessao(sessoes).cabe(new Sessao(LocalTime.parse("13:00:00"), filme2, sala)));
	}


}
