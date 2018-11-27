package com.ivan.github.core.net;

import com.ivan.github.GitHub;
import com.ivan.github.api.GitHubService;

/**
 * tools to request
 *
 * @author Iavn J. Lee on 2018-11-27 22:53.
 * @version v0.1
 * @since   v1.0
 */
public class HttpClient {

    public static GitHubService gitHubService() {
        return GitHub.appComponent().githubService();
    }
}
