// sudo su -s /bin/bash jenkins //Login as Jenkins user @ instance
// Generate key and add to repo
// ssh git@github.com //Test Key and add github to known hosts list
// Webhook @ Repo
// Serverless: Packaging service...
// Serverless: Excluding development dependencies...
// Serverless: Uploading CloudFormation file to S3...
// Serverless: Uploading artifacts...
// Serverless: Uploading service .zip file to S3 (30.23 KB)...
// Serverless: Validating template...
// Serverless: Updating Stack...
// Serverless: Checking Stack update progress...
// Serverless: Stack update finished...
// Service Information
// service: hello-world
// stage: staging
// region: us-east-1
// stack: hello-world-staging
// api keys:
//   None
// endpoints:
//   None
// functions:
//   hello_world: hello-world-staging-hello_world

pipeline {

    agent { dockerfile true }

    stages {
        stage ('Clone') {
            steps {
                checkout scm
            }
        }
        stage ('Packaging') {
            steps {
                echo 'Creating lambda zip files'
                sh './scripts/zip_handlers.sh'
            }
        }
        stage ('Test') {
            steps {
                echo 'tests'
            }
        }
        stage ('Deploy') {
            steps {
                echo 'Uploading lambdas to S3, instance must have AWSCLI configured or S3:ReadWrite Role attached'
                sh 'cd tmp && aws s3 sync . s3://campaign-manager-lambdas --delete --acl public-read'
            }
        }
        stage ('Erase Tmp Dir') {
            steps {
                sh 'cd .. && rm -rf tmp'
            }
        }
        stage ('Update Cloudformation') {
            steps {
                echo 'update CF'
            }
        }
    }
    post { 
        always { 
            cleanWs()
            // TODO: Cleanup step
            // docker system prune --force --all --volumes
        }
    }
}