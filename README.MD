mmall_learning

touch README.md 创建一个文件
touch .gitignore 创建一个ignore文件，这个文件配置哪些文件不需要上传到git
git init 初始化创建git


git status 查看文件变更(包括新增的还有修改的)
git add . 添加所有的变更文件
再git status 这时候变成黄色了
git commit -am 'project seccond commit'  （-a 是提交 -am '' 是把提交
        的message写在里面）这里的提交只是提交到了本地git仓库
git remote add origin git@github.com:1411651004/mmall_learning.git 
        这是连接远程仓库地址 
git branch 查看分支
git push -u origin master 这是见本地仓库内容推送到远程仓库(-u后加个-f是强制推送)
git pull 从远程更新代码（push前先pull）
git checkout -b fenzhi origin/master (-b是创建分支，origin／master代表在这个主分支下)
git push origin HEAD -u 将创建的分支推送到远程