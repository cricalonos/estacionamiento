pipeline {
	 //Donde se va a ejecutar el Pipeline
	 agent {
	 label 'Slave_Induccion'
	 }
	 
	 //Opciones específicas de Pipeline dentro del Pipeline
 	options {
		//Mantener artefactos y salida de consola para el # específico de ejecuciones recientes del Pipeline.
		buildDiscarder(logRotator(numToKeepStr: '3'))
		//No permitir ejecuciones concurrentes de Pipeline
		disableConcurrentBuilds()
 	}
 	
	//Una sección que define las herramientas para “autoinstalar” y poner en la PATH
	tools {
		jdk 'JDK8_Centos' //Preinstalada en la Configuración del Master
		gradle 'Gradle4.5_Centos' //Preinstalada en la Configuración del Master
	}
 	
 	//Aquí comienzan los “items” del Pipeline
	stages{
		stage('Unit Tests') {
			steps{
				echo "------------>Unit Tests<------------"
				sh 'gradle test --stacktrace'
				junit '**/build/jacoco/test-results/test/*.xml' //aggregate test results - JUnit
				jacoco classPattern:'**/build/classes/java', execPattern:'**/build/jacoco/test.exec', sourcePattern:'**/src/main/java'
			}
		}
		stage('Build') {
			steps{
				echo "------------>Build<------------"
				//Construir sin tarea test que se ejecutó previamente
				sh 'gradle --b ./build.gradle build -x test'
			}
		}
		stage('Static Code Analysis') {
			steps{
				echo '------------>Análisis de código estático<------------'
				withSonarQubeEnv('Sonar') {
					sh "${tool name: 'SonarScanner' , type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner"
				}
			}
		}
	}
	
	post {
		success {
			echo 'This will run only if successful'
			junit '**/build/jacoco/test-results/test/*.xml'
		}
		failure {
			echo 'This will run only if failed'
			mail (to: 'cristian.londono@ceiba.com.co', subject: "Failure Pipeline:${currentBuild.fullDisplayName}", 
			body: "Something is wrong with ${env.BUILD_URL}")
		}
	}
}