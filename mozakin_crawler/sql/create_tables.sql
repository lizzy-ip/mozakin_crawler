-- スレッド情報テーブル

DROP TABLE IF EXISTS thread_information;
CREATE TABLE thread_information (
	thread_id				INT4			NOT NULL
	, owner_name			VARCHAR(252)	NOT NULL
	, owner_id				VARCHAR(7)		NOT NULL
	, thread_create_date	TIMESTAMP		NOT NULL
	, message				TEXT			NOT NULL
	, message_color			VARCHAR(7)		NOT NULL
	, number_of_responses	INT2			NOT NULL
	, number_of_pages		INT2			NOT NULL
	, last_response_id		INT2			NOT NULL
	, image_server			VARCHAR(252)
	, image_id				VARCHAR(31)
	, create_date			TIMESTAMP		DEFAULT now()
	, update_date			TIMESTAMP		DEFAULT now()
	, is_update				boolean			NOT NULL
);


ALTER TABLE thread_information ADD PRIMARY KEY(thread_id);
CREATE INDEX thread_information_owner_name_index ON thread_information (owner_name);

COMMENT ON TABLE thread_information IS 'スレッド情報';
COMMENT ON COLUMN thread_information.thread_id				IS 'スレッドID';
COMMENT ON COLUMN thread_information.owner_name				IS 'オーナー名';
COMMENT ON COLUMN thread_information.owner_id				IS 'オーナーID';
COMMENT ON COLUMN thread_information.thread_create_date		IS 'スレッド作成日';
COMMENT ON COLUMN thread_information.message				IS 'メッセージ';
COMMENT ON COLUMN thread_information.message_color			IS 'メッセージ色';
COMMENT ON COLUMN thread_information.number_of_responses	IS '投稿数';
COMMENT ON COLUMN thread_information.number_of_pages		IS 'ページ数';
COMMENT ON COLUMN thread_information.last_response_id		IS '最終投稿ID';
COMMENT ON COLUMN thread_information.image_server			IS '画像サーバ名';
COMMENT ON COLUMN thread_information.image_id				IS '画像ID';
COMMENT ON COLUMN thread_information.create_date			IS '作成日';
COMMENT ON COLUMN thread_information.update_date			IS '更新日';
COMMENT ON COLUMN thread_information.is_update				IS '更新フラグ';

-- レスポンス情報テーブル

DROP TABLE IF EXISTS responses;
CREATE TABLE responses (
	thread_id				INT4			NOT NULL
	, response_id			INT4			NOT NULL
	, contributor_name		VARCHAR(252)	NOT NULL
	, contributor_id		VARCHAR(7)		NOT NULL
	, contribute_date		TIMESTAMP		NOT NULL
	, message				TEXT			NOT NULL
	, message_color			VARCHAR(7)		NOT NULL
	, image_server			VARCHAR(252)
	, image_id				VARCHAR(15)
	, movie_id				VARCHAR(15)
);


ALTER TABLE responses ADD PRIMARY KEY(thread_id, response_id);
CREATE INDEX responses_contributor_name_index ON thread_information (owner_name);

COMMENT ON TABLE responses IS 'レスポンス';
COMMENT ON COLUMN responses.thread_id				IS 'スレッドID';
COMMENT ON COLUMN responses.response_id				IS 'レスポンスID';
COMMENT ON COLUMN responses.contributor_name		IS '投稿者名';
COMMENT ON COLUMN responses.contributor_id			IS '投稿者ID';
COMMENT ON COLUMN responses.contribute_date			IS '投稿日';
COMMENT ON COLUMN responses.message					IS 'メッセージ';
COMMENT ON COLUMN responses.message_color			IS 'メッセージ色';
COMMENT ON COLUMN responses.image_server			IS '画像サーバ名';
COMMENT ON COLUMN responses.image_id				IS '画像ID';
COMMENT ON COLUMN responses.movie_id				IS '動画ID';

-- 画像テーブル

DROP TABLE IF EXISTS image_data;
CREATE TABLE image_data (
	thread_id		INT4			NOT NULL
	, response_id	INT4			NOT NULL
	, image_server	VARCHAR(252)	NOT NULL
	, image_id		VARCHAR(31)		NOT NULL
	, file_size		INT4			NOT NULL
	, raw_data		BYTEA			NOT NULL
);

ALTER TABLE image_data ADD PRIMARY KEY(thread_id, response_id);

COMMENT ON TABLE image_data IS '画像テーブル';
COMMENT ON COLUMN image_data.thread_id		IS 'スレッドID';
COMMENT ON COLUMN image_data.response_id		IS 'スレッドID';
COMMENT ON COLUMN image_data.image_server	IS '画像サーバ名';
COMMENT ON COLUMN image_data.image_id		IS '画像ID';
COMMENT ON COLUMN image_data.file_size		IS 'ファイルサイズ';
COMMENT ON COLUMN image_data.raw_data		IS '画像データ';

