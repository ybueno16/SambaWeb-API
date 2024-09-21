package com.br.SambaWebAPI.config;


import io.swagger.v3.oas.annotations.Parameter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Aspecto para processar automaticamente as anotações de operação do Swagger
 * para métodos de controladores anotados com {@link DefaultOperation}.
 */
@Aspect
@Component
public class SwaggerAspect {

    /**
     * Intercepta métodos de controladores anotados com {@link DefaultOperation}
     * para processar e definir valores padrão das anotações de operação do Swagger.
     *
     * @param joinPoint o ponto de junção representando a execução do método
     * @throws Throwable se ocorrer algum erro durante a reflexão
     */
    @Before("execution(public * .controller *(..))")
    public void processDefaultOperation(JoinPoint joinPoint) throws Throwable {
        Method method = getMethodFromJoinPoint(joinPoint).orElse(null);
        if (method != null) {
            DefaultOperation defaultOperation = AnnotationUtils.findAnnotation(method, DefaultOperation.class);
            if (defaultOperation != null) {
                Arrays.stream(defaultOperation.parameters()).forEach(this::processParameter);
            }
        }
    }

    /**
     * Processa e aplica configurações adicionais para parâmetros do Swagger.
     *
     * @param parameter o parâmetro Swagger a ser processado
     */
    private void processParameter(Parameter parameter) {
        System.out.printf("Processando parâmetro: %s%n", parameter.name());
    }

    /**
     * Obtém o método sendo executado a partir do ponto de junção.
     *
     * @param joinPoint o ponto de junção representando a execução do método
     * @return um Optional contendo o método sendo executado, ou vazio se não encontrado
     */
    private Optional<Method> getMethodFromJoinPoint(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        return Arrays.stream(targetClass.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .findFirst();
    }
}

