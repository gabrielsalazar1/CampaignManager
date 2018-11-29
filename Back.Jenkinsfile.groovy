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
        stage ('Install') {
            steps {
                sh 'pip install pymongo'
                sh 'sh scripts/install.sh'
            }
        }
        stage ('Test') {
            steps {
                sh 'sh scripts/test.sh'
            }
        }
        stage ('Version') {
            steps {
                sh 'G_VERSION=$(./scripts/version.sh)'
                sh 'echo "Build version: ${G_VERSION}"'
            }
        }
        // stage ('Coverage') {
        //     steps {
        //         sh 'make coverage'
        //     }
        // }
        // stage ('make build') {
        //     steps {
        //         sh 'make build'
        //     }
        // }
        stage ('Deploy') {
            steps {
                sh 'sh scripts/deploy.sh'
            }
        }
        // stage ('Deploy') {
        //     steps {
        //         sh 'cd build && aws s3 sync . s3://campaign-manager-belcorp --delete --acl public-read'
        //     }
        // }
    }
    post { 
        always { 
            cleanWs()
            // TODO: Cleanup step
            // docker system prune --force --all --volumes
        }
    }
}