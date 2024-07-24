了解到命令默认就是OP权限的，那么在文档中关于权限的部分可以简化。以下是更新后的使用文档：
# Cubic Transfer Plugin 使用文档
## 简介
Cubic Transfer Plugin 是一个用于创建和管理立方体传送区域的插件。通过本插件，管理员可以在游戏中设置立方体区域，并配置传送规则，实现玩家在不同位置或服务器间的传送。
## 安装
1. 将 `Cubic_Transfer_Plugin.jar` 文件放置于服务器的 `plugins` 文件夹中。
2. 重启服务器以加载插件。
## 命令列表
### `/ct create <立方体名>`
- **用途**：创建一个新的立方体传送区域。
### `/ct remove <立方体名>`
- **用途**：删除指定的立方体传送区域。
### `/ct set_name <旧立方体名> <新立方体名>`
- **用途**：修改立方体的名称。
### `/ct set_a <立方体名>`
- **用途**：设置立方体的点A位置为执行命令的玩家当前位置。
### `/ct set_b <立方体名>`
- **用途**：设置立方体的点B位置为执行命令的玩家当前位置。
### `/ct set_ics <立方体名> <触发类型>`
- **用途**：设置立方体的触发方式。触发类型可以是：0（走进范围）、1（方块交互）。
### `/ct set_mode <立方体名>`
- **用途**：修改立方体的传送模式。
### `/ct set_pos <立方体名>`
- **用途**：设置立方体的传送位置为执行命令的玩家当前位置。
### `/ct set_address <立方体名> <服务器IP>`
- **用途**：设置立方体传送的目标服务器IP。
### `/ct set_port <立方体名> <端口>`
- **用途**：设置立方体传送的目标服务器端口。
### `/ct list`
- **用途**：列出所有已创建的立方体传送区域及其详细信息。
## 注意事项
- 在设置立方体传送区域前，请确保服务器已经正确加载了插件。
- 使用命令时，确保输入的参数正确无误，以避免执行失败。
- 若要跨服务器传送，请确保目标服务器网络可达，并且端口设置正确。
## 常见问题
- **问题**：创建立方体后，无法正常传送。
  - **解决方法**：检查立方体的点A和点B是否已正确设置，以及传送模式和触发方式是否配置正确。
    通过以上步骤，管理员可以有效地使用Cubic Transfer Plugin管理游戏中的立方体传送区域。
