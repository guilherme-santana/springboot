package curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method=RequestMethod.GET, value="/cadastropessoa")
	public ModelAndView inicio(){
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		modelAndView.addObject("pessoaobj", new Pessoa());
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		modelAndView.addObject("pessoas", PessoasIt);
		return modelAndView;
	}

	//requestMapping intercepta a requisição sempre que a ação "salvarpessoa" for acionada
	@RequestMapping(method=RequestMethod.POST, value="**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa){
		//persiste no banco
		pessoaRepository.save(pessoa);
		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@RequestMapping(method= RequestMethod.GET, value="/listapessoas")
	public ModelAndView listarPessoas(){
		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView editar (@PathVariable("idpessoa") Long idpessoa){
		//carrega os dados de Pessoa do banco
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		//instanciar o view para injetar os dados na tela
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		//adiciona a pessoa obtida ao objeto pessoaobj
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;
	}

	@GetMapping("/excluirpessoa/{idpessoa}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView excluir (@PathVariable("idpessoa") Long idpessoa){
		// Método pronto para excluir pessoa
		pessoaRepository.deleteById(idpessoa);

		//instanciar o view para injetar os dados na tela
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		//Obtem do banco a lista de pessoas
		Iterable<Pessoa> PessoasIt = pessoaRepository.findAll();
		//adiciona na viw a lista de pessoas. Atributo pessoas criado no html
		andView.addObject("pessoas", PessoasIt);
		//passa a lista de objetos vazia para poder carregar a tela de cadastro sem erros
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}
	//** utilizado para ignorar qualquer coisa que estiver antes de /pesquisapessoa utilizado sempre quando o metodo for POST 
	@PostMapping("**/pesquisapessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa){
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
		andView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa.toLowerCase()));
		andView.addObject("pessoaobj", new Pessoa());
		return andView;
	}
	
	@GetMapping("/telefones/{idpessoa}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView telefones (@PathVariable("idpessoa") Long idpessoa){
		//carrega os dados de Pessoa do banco
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		//instanciar o view para injetar os dados na tela
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		//adiciona a pessoa obtida ao objeto pessoaobj
		modelAndView.addObject("pessoaobj", pessoa.get());
		modelAndView.addObject("telefones", telefoneRepository.getTelefone(idpessoa));
		return modelAndView;
	}

	@PostMapping("**/addfonepessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid){
		Pessoa pessoa =  pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		ModelAndView modelV = new ModelAndView("cadastro/telefones");
		modelV.addObject("pessoaobj", pessoa);
		modelV.addObject("telefones", telefoneRepository.getTelefone(pessoaid));
		return modelV;
	}

	@GetMapping("/excluirtelefone/{idtelefone}")
	//@PathVariable serve para pegar a variavel que vem na url
	public ModelAndView telefoneExcluir (@PathVariable("idtelefone") Long idtelefone){
		Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
		telefoneRepository.deleteById(idtelefone);
		ModelAndView andView = new ModelAndView("cadastro/telefones");
		andView.addObject("pessoaobj", pessoa);
		telefoneRepository.getTelefone(pessoa.getId()).clear();
		andView.addObject("telefones", telefoneRepository.getTelefone(pessoa.getId()));
		return andView;
	}


}
