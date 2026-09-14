    package br.com.aula.techx.controller;
    import br.com.aula.techx.model.Tarefas;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RequestMapping("/api/tarefas")
    @RestController
        public class TarefaController {
            private final Tarefas tarefas = new Tarefas(
                    "Aula",
                    "TechX",
                    "21/02/2010" );

            @GetMapping("/tarefas")
            public Tarefas getTarefas() {
                return tarefas;
            }

            @GetMapping("/descricao")
            public String getDescricao() {
                return tarefas.getDescricao();
            }
            @GetMapping("/lembrete")
            public String getLembrete() {
                return tarefas.getDescricao();
            }
        }
