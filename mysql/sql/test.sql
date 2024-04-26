LOCK TABLES `chat` WRITE;
INSERT INTO `chat` (user_id, chat_name, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified) VALUES
(2, '测试对话', 2, 'zmr', NULL, NULL, '2024-04-20 00:01:00', NULL);
UNLOCK TABLES;

LOCK TABLES `message` WRITE;
INSERT INTO `message` (chat_id, content, type, creator_id, creator_name, modifier_id, modifier_name, gmt_create, gmt_modified) VALUES 
(1, '你能解释一下 ChatGPT 是如何工作的吗？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:00:00', NULL),
(1, 'ChatGPT 能够处理哪些类型的数据？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:10:00', NULL),
(1, 'ChatGPT 在多大程度上能够理解语境？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:20:00', NULL),
(1, 'ChatGPT 是否可以用于自然语言处理任务？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:30:00', NULL),
(1, 'ChatGPT 是如何生成回复的？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:40:00', NULL),
(1, 'ChatGPT 的训练数据集包括哪些内容？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 01:50:00', NULL),
(1, 'ChatGPT 的性能受到哪些因素的影响？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:00:00', NULL),
(1, 'ChatGPT 能够处理多种语言吗？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:10:00', NULL),
(1, 'ChatGPT 是否具有记忆功能？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:20:00', NULL),
(1, 'ChatGPT 能够进行推理和逻辑推断吗？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:30:00', NULL),
(1, 'ChatGPT 是否具有情感分析的能力？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:40:00', NULL),
(1, 'ChatGPT 对于长文本的处理能力如何？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 02:50:00', NULL),
(1, 'ChatGPT 是否可以生成代码？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:00:00', NULL),
(1, 'ChatGPT 是否可以进行语言翻译？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:10:00', NULL),
(1, 'ChatGPT 在处理口语化语言时表现如何？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:20:00', NULL),
(1, 'ChatGPT 是否具有对话管理的能力？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:30:00', NULL),
(1, 'ChatGPT 是否可以进行知识推理？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:40:00', NULL),
(1, 'ChatGPT 是否能够理解图片或其他媒体类型？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 03:50:00', NULL),
(1, 'ChatGPT 是否可以进行信息检索？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:00:00', NULL),
(1, 'ChatGPT 是否受到语言模型的限制？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:10:00', NULL),
(1, 'ChatGPT 是否会受到偏见和歧视的影响？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:20:00', NULL),
(1, 'ChatGPT 的生成回复是否总是准确的？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:30:00', NULL),
(1, 'ChatGPT 是否有自我学习的能力？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:40:00', NULL),
(1, 'ChatGPT 是否可以识别文本中的错误或不准确信息？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 04:50:00', NULL),
(1, 'ChatGPT 是否具有对话衔接的能力？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:00:00', NULL),
(1, 'ChatGPT 是否能够处理带有含糊不清语言的对话？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:10:00', NULL),
(1, 'ChatGPT 是否可以用于智能助手应用？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:20:00', NULL),
(1, 'ChatGPT 是否能够识别对话中的语气和情感？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:30:00', NULL),
(1, 'ChatGPT 是否可以进行对话摘要和总结？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:40:00', NULL),
(1, 'ChatGPT 是否能够自动生成问题？', 0, 2, 'zmr', NULL, NULL, '2024-04-20 05:50:00', NULL);
UNLOCK TABLES;